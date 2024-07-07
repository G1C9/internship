package org.example.internship_receiver.repo

import org.example.internship_receiver.entitys.DebitAccount
import org.example.internship_receiver.entitys.OperationsInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OperationsInfoRepo: JpaRepository<OperationsInfo, Long> {

    fun existsByTransactionUID(transactionUID: String) : Boolean

}