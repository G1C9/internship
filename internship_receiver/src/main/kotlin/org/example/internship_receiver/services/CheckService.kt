package org.example.internship_receiver.services

import org.example.internship_libs.dto.Doc
import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.example.internship_receiver.repo.ClientsRepo
import org.example.internship_receiver.repo.DebitAccountRepo
import org.example.internship_receiver.repo.OperationsInfoRepo
import org.springframework.stereotype.Service
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

@Service
class CheckService(
    private val clientsRepo: ClientsRepo,
    private val debitAccountRepo: DebitAccountRepo,
    private val operationsInfoRepo: OperationsInfoRepo
) {
    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private fun doIfError(doc: Doc, errorMessage: String) {
        val errorUser = jsonMapper().writeValueAsString(doc)
        val fos = FileOutputStream("C:\\Users\\1\\IdeaProjects\\moduleProject\\internship_receiver\\error_transactions\\${doc.transactionUID}.txt")
        fos.write(errorUser.toByteArray())
        fos.close()
        //val path = Paths.get("error_transactions/${doc.transactionUID}.txt")
        //Files.newBufferedWriter(path, Charsets.UTF_8).use { it.write(errorUser) }
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
        if (doc.signDocuments != null) {
            logger.info("Sign exists!")
        } else {
            doIfError(doc, "Sign is not exists")
        }
    }

    fun existOperation(doc: Doc) {
        if (operationsInfoRepo.existsByTransactionUID(
                doc.transactionUID!!
            )
        ) {
            doIfError(doc, "Operation is exist")
        } else {
            logger.info("Operation is not exist")
        }
    }

}