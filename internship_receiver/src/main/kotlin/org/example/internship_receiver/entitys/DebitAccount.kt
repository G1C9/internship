package org.example.internship_receiver.entitys

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
data class DebitAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "clients_id")
    var clientAccountId: Clients? = null,

    @Column
    var accountUID: String? = null,

    @Column
    @OneToMany(mappedBy = "debitAccountId")
    var operations: List<OperationsInfo>? = null
)


