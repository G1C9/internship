package org.example.internship_receiver.services

import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMqListener(
    private val operationService: OperationService
) {

    @RabbitListener(queues = ["myQueue"])
    fun processQueue1(message: Message) {
        operationService.processOperation(message.body)
    }

    /*@RabbitListener(queues = ["myQueue"], containerFactory = "firstRabbitListenerContainerFactory")
    fun processQueue1(message: org.springframework.amqp.core.Message) {
        cryptoService.checkSignXmlFile(message.body)
    }

    @RabbitListener(queues = ["myQueue"], containerFactory = "secondRabbitListenerContainerFactory")
    fun processQueue2(message: org.springframework.amqp.core.Message) {
        cryptoService.checkSignXmlFile(message.body)
    }*/


}