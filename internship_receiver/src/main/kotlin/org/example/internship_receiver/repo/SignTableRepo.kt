package org.example.internship_receiver.repo

import org.example.internship_receiver.entitys.SignTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SignTableRepo: JpaRepository<SignTable, Long> {

}