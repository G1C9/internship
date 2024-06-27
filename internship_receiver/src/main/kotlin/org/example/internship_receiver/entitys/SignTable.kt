package org.example.internship_receiver.entitys

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne

@Entity
data class SignTable(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var content: ByteArray? = null,
    var isAccess: String? = null,

    @OneToOne(mappedBy = "signId")
    var operationsId: OperationsInfo? = null
)
