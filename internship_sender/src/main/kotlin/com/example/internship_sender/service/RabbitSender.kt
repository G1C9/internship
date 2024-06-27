package com.example.internship_sender.service

import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import kotlin.ByteArray
import kotlin.apply

@Service
class RabbitSender (
    //private val firstRabbitTemplate: RabbitTemplate,
    //private val secondRabbitTemplate: RabbitTemplate,

    private val rabbitTemplate: RabbitTemplate
){

    fun sendMessage(
        file: ByteArray
    ) {
        /*firstRabbitTemplate.apply { routingKey = "myQueue" }.convertAndSend(Message(xmlFile))
        secondRabbitTemplate.apply { routingKey = "myQueue" }.convertAndSend(Message(xmlFile))*/
        rabbitTemplate.apply { routingKey = "myQueue" }.convertAndSend(Message(file))
    }

}