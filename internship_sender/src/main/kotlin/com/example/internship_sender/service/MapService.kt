package com.example.internship_sender.service

import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.example.internship_libs.dto.Doc
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Service
import java.io.File

@Service
class MapService(
    private val maskingService: MaskingService
) {

    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    @ServiceActivator
    fun mapXmlFile(file: File): Doc? {
        logger.info("Found new xml file!")
        val xmlMapper = XmlMapper()
        val doc = xmlMapper.readValue(file, Doc::class.java)
        val dataForSign = maskingService.maskData(File("C:\\Users\\1\\IdeaProjects\\moduleProject\\internship_sender\\src\\main\\resources\\documents\\${doc.transactionUID}.jpg"))
        var signContent = xmlMapper.writeValueAsBytes(dataForSign)
        doc.signDocuments = signContent
        return doc
    }

    @ServiceActivator
    fun mapJsonFile(file: File): Doc? {
        logger.info("Found new json file!")
        val jsonMapper = ObjectMapper()
        jsonMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        val doc = jsonMapper.readValue(file, Doc::class.java)
        val dataForSign = maskingService.maskData(File("C:\\Users\\1\\IdeaProjects\\moduleProject\\internship_sender\\src\\main\\resources\\documents\\${doc.transactionUID}.jpg"))
        var signContent = jsonMapper.writeValueAsBytes(dataForSign)
        doc.signDocuments = signContent
        return doc
    }
}