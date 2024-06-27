package org.example.internship_receiver.services

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.example.internship_receiver.entitys.SignTable
import org.example.internship_receiver.repo.SignTableRepo
import org.springframework.stereotype.Service
import ru.CryptoPro.CAdES.CAdESSignature
import ru.CryptoPro.CAdES.CAdESType
import ru.CryptoPro.Crypto.CryptoProvider
import ru.CryptoPro.JCSP.JCSP
import ru.CryptoPro.reprov.RevCheck
import java.security.KeyStore
import java.security.Security
import java.security.cert.Certificate
import java.security.cert.X509CRL
import java.security.cert.X509Certificate
import java.util.Base64

@Service
class CryptoService(
    private val signTableRepo: SignTableRepo,
) {
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    private lateinit var cert: Certificate
    private val objectMapper = ObjectMapper()

    @PostConstruct
    fun initCryptoPro() {

        System.setProperty("file.encoding", "UTF-8")
        Security.addProvider(JCSP()) // Провайдер JCSP
        Security.addProvider(RevCheck()) // Провайдер проверки сертификатов JCPRevCheck (провайдер отзыва)
        Security.addProvider(CryptoProvider()) // Провайдер шифрования JCryptoP

        val ks = KeyStore.getInstance("HDIMAGE", "JCSP")
        ks.load(null, null)
        cert = ks.getCertificate("test")

    }

    fun verifySignData(signData: ByteArray?): Boolean {
        try {
            val chain = mutableSetOf<X509Certificate>(cert as X509Certificate)
            val cRLs = mutableSetOf<X509CRL>()
            val cadesSignature = CAdESSignature(signData, null, CAdESType.CAdES_BES)
            cadesSignature.verify(chain, cRLs)
            logger.info("Верификация успешна")
            return true
        } catch (e: Exception) {
            logger.info("Ошибка верификации\nСообщение об ошибке: ${e.stackTraceToString()}")
            return false
        }
    }

    fun saveSign(doc: Doc) {
        val entity = signTableRepo.save(SignTable(content = doc.sign, isAccess = "NEW"))
        if (verifySignData(Base64.getDecoder().decode(doc.sign)) == true) {
            signTableRepo.save(entity.apply { isAccess = "SUCCESS" })
        } else {
            signTableRepo.save(entity.apply { isAccess = "ERROR" })
        }
    }
}

