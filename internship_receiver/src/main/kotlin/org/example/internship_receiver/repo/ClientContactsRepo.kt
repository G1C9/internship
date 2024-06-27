package org.example.internship_receiver.repo

import org.example.internship_receiver.entitys.ClientContacts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientContactsRepo: JpaRepository<ClientContacts, Long> {
}