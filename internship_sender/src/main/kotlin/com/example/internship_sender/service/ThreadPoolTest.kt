/*
package com.example.internship_sender.service

import com.example.internship_sender.dto.json_xml.Doc
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.File
import java.util.concurrent.Executors

@Service
class ThreadPoolTest {

    init {
        val pool = Executors.newFixedThreadPool(3);
        val list = listOf(1, 2, 3)
        val newList = list.forEach {
            //newList.add(it * 2)
            pool.execute{it * 2}
        }
        pool.shutdown()

        val file = File("C:\\Users\\1\\IdeaProjects\\moduleProject\\internship_sender\\src\\main\\resources\\jsonFiles\\test.json")
        val jsonMapper = ObjectMapper()
        val doc = jsonMapper.readValue(file, Doc::class.java)
        val senderClient = doc.senderClient
        val receiverClient = doc.receiverClient
        val debitAccount = doc.debitAccount
        senderClient?.contacts?.forEach {
            println(it)
        }
    }
}
*/
