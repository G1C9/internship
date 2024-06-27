package com.example.internship_sender.service

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Service
import ru.CryptoPro.CAdES.CAdESSignature
import ru.CryptoPro.CAdES.CAdESType
import ru.CryptoPro.Crypto.CryptoProvider
import ru.CryptoPro.JCP.*
import ru.CryptoPro.JCP.KeyStore.JCPPrivateKeyEntry
import ru.CryptoPro.JCSP.JCSP
import ru.CryptoPro.reprov.RevCheck
import java.io.ByteArrayOutputStream
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Security
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.Base64

@Service
class CryptoService(
    private val rabbitSender: RabbitSender
) {
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private lateinit var cert: Certificate
    private lateinit var privateKey: PrivateKey

    init {
        System.setProperty("com.sun.security.enableCRLDP", "true")
        System.setProperty("com.ibm.security.enableCRLDP", "true")
        System.setProperty("file.encoding", "UTF-8")
        Security.addProvider(JCSP()) // Провайдер JCSP
        Security.addProvider(RevCheck()) // Провайдер проверки сертификатов JCPRevCheck (провайдер отзыва)
        Security.addProvider(CryptoProvider()) // Провайдер шифрования JCryptoP

        val ks = KeyStore.getInstance("HDIMAGE", "JCSP")
        ks.load(null, null)

        cert = ks.getCertificate("test")
        privateKey = (ks.getEntry("test", KeyStore.PasswordProtection("123456".toCharArray())) as JCPPrivateKeyEntry).privateKey

        logger.info("Crypto pro successful initialized!")
    }

    private fun signData(data: ByteArray): ByteArray {
        val chain = mutableListOf<X509Certificate>(cert as X509Certificate)
        val cadesSignature = CAdESSignature(false)
        cadesSignature.addSigner(JCSP.PROVIDER_NAME, JCP.GOST_DIGEST_OID,
            JCP.GOST_EL_KEY_OID, privateKey, chain, CAdESType.CAdES_BES, null, false);

        val signatureStream = ByteArrayOutputStream()
        cadesSignature.open(signatureStream) // подготовка контекста
        cadesSignature.update(data) // вычисление хэш-кода
        cadesSignature.close() // создание подписи с выводом в signatureStream
        signatureStream.close()

        return signatureStream.toByteArray()
    }

    @ServiceActivator
    fun sendXmlSign(docXml: Doc) {
        docXml.sign = Base64.getEncoder().encode(signData(docXml.sign!!))
        val docXmlString = ObjectMapper().writeValueAsBytes(docXml)
        logger.info(String(docXmlString))
        logger.info("Data xml successful signed!")
        rabbitSender.sendMessage(docXmlString)

    }

    @ServiceActivator
    fun sendJsonSign(docJson: Doc) {

        docJson.sign = Base64.getEncoder().encode(signData(docJson.sign!!))
        val docJsonString = ObjectMapper().writeValueAsBytes(docJson)
        logger.info(String(docJsonString))
        logger.info("Data json successful signed!")
        rabbitSender.sendMessage(docJsonString)

    }

}