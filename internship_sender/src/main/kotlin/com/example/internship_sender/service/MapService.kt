package com.example.internship_sender.service

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Service
import java.io.File

@Service
class MapService {

    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    @ServiceActivator
    fun mapXmlFile(file: File): Doc? {
        logger.info("Found new xml file!")
        val xmlMapper = XmlMapper()
        val docXml = xmlMapper.readValue(file, Doc::class.java)
        val dataForSign = docXml.senderClient
        var signContent = xmlMapper.writeValueAsBytes(dataForSign)
        docXml.sign = signContent
        return docXml
    }

    @ServiceActivator
    fun mapJsonFile(file: File): Doc? {
        logger.info("Found new json file!")
        val jsonMapper = ObjectMapper()
        jsonMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        val docXml = jsonMapper.readValue(file, Doc::class.java)
        val dataForSign = docXml.senderClient
        var signContent = jsonMapper.writeValueAsBytes(dataForSign)
        docXml.sign = signContent
        return docXml
    }

}