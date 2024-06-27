package org.example.internship_receiver.entitys

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Clients(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var clientUID: String? = null,

    @Column
    var clientSurname: String? = null,

    @Column
    var clientName: String? = null,

    @Column
    var clientPatronymic: String? = null,

    @Column
    var bankName: String? = null,

    @Column
    var clientShortName: String? = null,

    @Column
    @OneToMany(mappedBy = "clientAccountId")
    var debitAccounts: List<DebitAccount>? = null,

    @Column
    @OneToMany(mappedBy = "clientIdContacts")
    var clientIdContacts: List<ClientContacts>? = null
)