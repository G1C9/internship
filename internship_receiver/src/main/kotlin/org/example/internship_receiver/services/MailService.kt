package org.example.internship_receiver.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val javaMailSender: JavaMailSender
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    fun sendMessage(subject: String, text: String, from: String, to: String) {
        logger.info("Sending message to email...")
        javaMailSender.send(SimpleMailMessage().apply {
            setFrom(from)
            setTo(to)
            setSubject(subject)
            setText(text)
        })
        logger.info("Message to email successful sending!")
    }
}