package org.example.internship_receiver.repo

import org.example.internship_receiver.entitys.Clients
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientsRepo: JpaRepository<Clients, Long> {

    fun existsByClientUID(clientUid: String): Boolean

    fun findByClientUID(clientUid: String): Clients

}