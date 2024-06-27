package org.example.internship_receiver.services

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.internship_receiver.entitys.OperationsInfo
import org.example.internship_receiver.repo.OperationsInfoRepo
import org.springframework.stereotype.Service

@Service
class OperationService (
    private val checkService: CheckService,
    private val operationsInfoRepo: OperationsInfoRepo,
    private val cryptoService: CryptoService
) {
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    private val objectMapper = ObjectMapper()

    fun saveOperation(dateAction: String, amount: Double) {
        operationsInfoRepo.save(OperationsInfo(
            dateAction = dateAction,
            sum = amount
        ))
        logger.info("Operation successful saved!")
    }

    fun processOperation(check: ByteArray) {
        try {
            logger.info("Received new message")
            val doc = objectMapper.readValue(check, Doc::class.java)
            logger.info("ClientUID: ${doc.senderClient?.clientUID}")
            checkService.existClient(doc)
            checkService.existDebitAccount(doc)
            checkService.existSign(doc)
            checkService.existOperation(doc)
            cryptoService.saveSign(doc)
            saveOperation(doc.dateAction!!, doc.debitAccount?.amount!!)
        } catch (e: Exception) {
            logger.error("Error saving operation:\n" +
                    "MESSAGE: ${e.stackTraceToString()}")
        }
    }
}