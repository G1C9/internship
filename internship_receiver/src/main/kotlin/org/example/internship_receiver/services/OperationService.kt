package org.example.internship_receiver.services

import org.example.internship_libs.dto.Doc
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.internship_receiver.entitys.OperationsInfo
import org.example.internship_receiver.repo.OperationsInfoRepo
import org.springframework.stereotype.Service

@Service
class OperationService(
    private val checkService: CheckService,
    private val operationsInfoRepo: OperationsInfoRepo,
    private val cryptoService: CryptoService,
    private val mailService: MailService
) {
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    private val objectMapper = ObjectMapper()

    fun saveOperation(dateAction: String, amount: Double, transactionUID: String) {
        operationsInfoRepo.save(
            OperationsInfo(
                transactionUID = transactionUID,
                dateAction = dateAction,
                sum = amount
            )
        )
        logger.info("Operation successful saved!")
    }

    fun processOperation(check: ByteArray) {
        try {
            logger.info("Received new message")
            val doc = objectMapper.readValue(check, Doc::class.java)
            logger.info("ClientUID: ${doc.senderClient?.clientUID}")
            logger.info("AccountUID: ${doc.debitAccount?.accountUID}")
            logger.info("TransactionUID: ${doc.transactionUID}")
            checkService.existClient(doc)
            checkService.existDebitAccount(doc)
            checkService.existSign(doc)
            checkService.existOperation(doc)
            cryptoService.saveSign(doc)
            saveOperation(doc.dateAction!!, doc.debitAccount?.amount!!, doc.transactionUID!!)
            logger.info("Operation successful saved")
            mailService.sendMessage(
                "Operation successful saved",
                "ClientUID: ${doc.senderClient?.clientUID}\n" +
                        "AccountUID: ${doc.debitAccount?.accountUID}\n" +
                        "Amount: ${doc.debitAccount?.amount}\n" +
                        "DateAction: ${doc.dateAction}",
                "test678912342@yandex.ru",
                "test678912342@yandex.ru"
            )
        } catch (e: Exception) {
            logger.error(
                "Error process operation | MESSAGE: ${e.message}"
            )
        }
    }
}