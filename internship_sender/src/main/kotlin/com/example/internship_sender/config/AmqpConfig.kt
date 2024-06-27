package com.example.internship_sender.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig {

    /*@Value("\${spring.rabbitmq.host1.host}")
    private lateinit var firstHostRabbit: String

    @Value("\${spring.rabbitmq.host1.port}")
    private lateinit var firstPortRabbit: String

    @Value("\${spring.rabbitmq.host1.virtual-host}")
    private lateinit var firstVirtualHostRabbit: String

    @Value("\${spring.rabbitmq.host1.username}")
    private lateinit var firstUsernameRabbit: String

    @Value("\${spring.rabbitmq.host1.password}")
    private lateinit var firstPassword: String

    @Value("\${spring.rabbitmq.host2.host}")
    private lateinit var secondHostRabbit: String

    @Value("\${spring.rabbitmq.host2.port}")
    private lateinit var secondPortRabbit: String

    @Value("\${spring.rabbitmq.host2.virtual-host}")
    private lateinit var secondVirtualHostRabbit: String

    @Value("\${spring.rabbitmq.host2.username}")
    private lateinit var secondUsernameRabbit: String

    @Value("\${spring.rabbitmq.host2.password}")
    private lateinit var secondPasswordRabbit: String*/

    @Bean
    fun myQueue(): Queue {
        return Queue("myQueue", true)
    }

    /*@Bean
    fun firstRabbitTemplate() = RabbitTemplate(CachingConnectionFactory().apply {
        setAddresses("$firstHostRabbit:$firstPortRabbit")
        setVirtualHost(firstVirtualHostRabbit)
        setUsername(firstUsernameRabbit)
        setPassword(firstPassword)
    })

    @Bean
    fun secondRabbitTemplate() = RabbitTemplate(CachingConnectionFactory().apply {
        setAddresses("$secondHostRabbit:$secondPortRabbit")
        setVirtualHost(secondVirtualHostRabbit)
        setUsername(secondUsernameRabbit)
        setPassword(secondPasswordRabbit)
    })*/
}

