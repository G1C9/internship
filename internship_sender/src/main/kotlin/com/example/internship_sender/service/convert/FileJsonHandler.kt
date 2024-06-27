package com.example.internship_sender.service.convert

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.databind.json.JsonMapper
import java.io.File

class FileJsonHandler: FileHandler {

    override fun handling(file: File) {
        val jsonMapper = JsonMapper()
        val docXml = jsonMapper.readValue(file, Doc::class.java)
        val senderClient = docXml.senderClient
        val receiverClient = docXml.receiverClient
        val debitAccount = docXml.debitAccount
    }

}