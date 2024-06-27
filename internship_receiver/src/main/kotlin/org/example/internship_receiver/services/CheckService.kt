package org.example.internship_receiver.services

import com.example.internship_sender.dto.Doc
import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.example.internship_receiver.repo.ClientsRepo
import org.example.internship_receiver.repo.DebitAccountRepo
import org.example.internship_receiver.repo.OperationsInfoRepo
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@Service
class CheckService (
    private val clientsRepo: ClientsRepo,
    private val debitAccountRepo: DebitAccountRepo,
    private val operationsInfoRepo: OperationsInfoRepo
){
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private fun doIfError(doc: Doc, errorMessage: String) {
        logger.error(errorMessage)
        val errorUser = jsonMapper().writeValueAsString(doc)
        val path = Paths.get("error_transactions/${doc.senderClient?.clientUID}-${doc.dateAction}.txt")
        Files.newBufferedWriter(path, Charsets.UTF_8).use { it.write(errorUser) }
        throw Exception(errorMessage)
    }

    fun existClient(doc: Doc) {
        if (clientsRepo.existsByClientUID(doc.senderClient?.clientUID!!)) {
            logger.info("User has already been added")
        } else {
            doIfError(doc, "User does not exist")
        }
    }

    fun existDebitAccount(doc: Doc) {
        if (debitAccountRepo.existsByAccountUID(doc.debitAccount?.accountUID!!)) {
            logger.info("DebitAccount is exist")
        } else {
            doIfError(doc, "DebitAccount is not exist")
        }
    }

    fun existSign(doc: Doc) {
        if (doc.sign != null) {
            logger.info("Sign exists!")
        } else {
            doIfError(doc, "Sign is not exists")
        }
    }

    fun existOperation(doc: Doc) {
        if (operationsInfoRepo.existsByDateActionAndSumAndDebitAccountId(
                doc.dateAction!!,
                doc.debitAccount?.amount,
                debitAccountRepo.findByAccountUID(doc.debitAccount?.accountUID!!)
            )) {
            doIfError(doc, "Operation is exist")
        } else {
            logger.info("Operation is not exist")
        }
    }

}