package org.example.internship_receiver.services

import org.example.internship_libs.dto.Doc
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.apache.commons.io.output.ByteArrayOutputStream
import org.example.internship_receiver.entitys.SignTable
import org.example.internship_receiver.repo.SignTableRepo
import org.springframework.stereotype.Service
import ru.CryptoPro.CAdES.EnvelopedSignature
import ru.CryptoPro.Crypto.CryptoProvider
import ru.CryptoPro.JCP.KeyStore.JCPPrivateKeyEntry
import ru.CryptoPro.JCSP.JCSP
import ru.CryptoPro.reprov.RevCheck
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Security
import java.security.cert.Certificate
import java.security.cert.X509CRL
import java.security.cert.X509Certificate
import java.util.Base64

@Service
class CryptoService(
    private val signTableRepo: SignTableRepo,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    private lateinit var cert: Certificate
    private lateinit var privateKey: PrivateKey

    @PostConstruct
    fun initCryptoPro() {

        System.setProperty("file.encoding", "UTF-8")
        Security.addProvider(JCSP()) // Провайдер JCSP
        Security.addProvider(RevCheck()) // Провайдер проверки сертификатов JCPRevCheck (провайдер отзыва)
        Security.addProvider(CryptoProvider()) // Провайдер шифрования JCryptoP

        val ks = KeyStore.getInstance("HDIMAGE", "JCSP")
        ks.load(null, null)
        cert = ks.getCertificate("test2")
        privateKey = (ks.getEntry("test2", KeyStore.PasswordProtection("123456".toCharArray())) as JCPPrivateKeyEntry).privateKey

    }

    fun decryptData(signData: ByteArray?): Boolean {
        try {
            val chain = mutableSetOf<X509Certificate>(cert as X509Certificate)
            val cRLs = mutableSetOf<X509CRL>()

            val os = ByteArrayOutputStream()
            val envelopedSignature = EnvelopedSignature(ByteArrayInputStream(signData))
            envelopedSignature.decrypt(cert as X509Certificate, privateKey, os)
            os.close()

            val fos = FileOutputStream("result.jpg")
            fos.write(Base64.getDecoder().decode(Base64.getDecoder().decode(String(os.toByteArray()).replace("\"", "").toByteArray())))
            fos.close()

            logger.info("Data xml successful decoded!")
            return true
        } catch (e: Exception) {
            logger.info("Encoding error | MESSAGE: ${e.message}")
            return false
        }
    }

    fun saveSign(doc: Doc) {
        val entity = signTableRepo.save(SignTable(content = doc.signDocuments, isAccess = "NEW"))
        if (decryptData(Base64.getDecoder().decode(doc.signDocuments)) == true) {
            signTableRepo.save(entity.apply { isAccess = "SUCCESS" })
        } else {
            signTableRepo.save(entity.apply { isAccess = "ERROR" })
        }
    }
}

