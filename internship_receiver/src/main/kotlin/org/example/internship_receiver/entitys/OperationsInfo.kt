package org.example.internship_receiver.entitys

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
data class OperationsInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "debit_account_id")
    var debitAccountId: DebitAccount? = null,

    @Column
    var dateAction: String? = null,

    @Column
    var transactionUID: String? = null,

    @Column
    var sum: Double? = null,

    @OneToOne
    @JoinColumn(name = "sign_table_id")
    var signId: SignTable? = null
)

