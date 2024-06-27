package com.example.internship_sender.config

import com.example.internship_sender.service.CryptoService
import com.example.internship_sender.service.MapService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.Pollers
import org.springframework.integration.file.dsl.Files
import java.io.File

@Configuration
class IntegrationConfig(
    private val cryptoService: CryptoService,
    private val mapService: MapService,
) {

    @Bean
    fun xmlFileReadingFlow(): IntegrationFlow =
        IntegrationFlow.from(
            Files.inboundAdapter(File("C:\\Users\\1\\IdeaProjects\\moduleProject\\internship_sender\\src\\main\\resources\\xmlFiles"))
                .preventDuplicates(false)
                .patternFilter("*.xml")
        ) { e -> e.poller(Pollers.fixedRate(60000)) }
            .handle(mapService, "mapXmlFile")
            .handle(cryptoService, "sendXmlSign")
            .channel("nullChannel")
            .get()

    @Bean
    fun jsonFileReadingFlow(): IntegrationFlow =
        IntegrationFlow.from(
            Files.inboundAdapter(File("C:\\Users\\1\\IdeaProjects\\moduleProject\\internship_sender\\src\\main\\resources\\jsonFiles"))
                .preventDuplicates(false)
                .patternFilter("*.json")
        ) { e -> e.poller(Pollers.fixedRate(60000)) }
            .handle(mapService, "mapJsonFile")
            .handle(cryptoService, "sendJsonSign")
            .channel("nullChannel")
            .get()

}