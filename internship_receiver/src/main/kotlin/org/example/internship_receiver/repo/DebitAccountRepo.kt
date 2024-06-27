package org.example.internship_receiver.repo

import org.example.internship_receiver.entitys.DebitAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebitAccountRepo: JpaRepository<DebitAccount, Long> {

    fun existsByAccountUID(accountUid: String): Boolean

    fun findByAccountUID(accountUid: String): DebitAccount

}