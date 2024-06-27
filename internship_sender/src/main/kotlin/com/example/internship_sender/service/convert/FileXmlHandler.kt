package com.example.internship_sender.service.convert

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import java.io.File

class FileXmlHandler: FileHandler {

    override fun handling(file: File) {
        val xmlMapper = XmlMapper()
        val docXml = xmlMapper.readValue(file, Doc::class.java)
        val senderClient = docXml.senderClient
        val receiverClient = docXml.receiverClient
        val debitAccount = docXml.debitAccount
    }

}

