package org.example.internship_receiver

import org.example.internship_receiver.services.OperationService
import kotlin.jvm.java

//@org.springframework.amqp.rabbit.annotation.EnableRabbit // Активация обработки аннотаций @RabbitListener
@org.springframework.stereotype.Component
class RabbitMqListener(
    private val operationService: OperationService
) {
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = ["myQueue"])
    fun processQueue1(message: org.springframework.amqp.core.Message) {
        operationService.processOperation(message.body)
    }

    /*@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = ["myQueue"], containerFactory = "firstRabbitListenerContainerFactory")
    fun processQueue1(message: org.springframework.amqp.core.Message) {
        cryptoService.checkSignXmlFile(message.body)
    }

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = ["myQueue"], containerFactory = "secondRabbitListenerContainerFactory")
    fun processQueue2(message: org.springframework.amqp.core.Message) {
        cryptoService.checkSignXmlFile(message.body)
    }*/

}