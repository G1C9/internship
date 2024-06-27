package com.example.internship_sender.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

class SenderClient {

    @JvmField
    @JsonProperty("ClientUID")
    var clientUID: String? = null

    @JvmField
    @JsonProperty("Surname")
    var surname: String? = null

    @JvmField
    @JsonProperty("Name")
    var clientName: String? = null

    @JvmField
    @JsonProperty("Patronymic")
    var patronymic: String? = null

    @JvmField
    @JsonProperty("SenderDebitAccount")
    var senderDebitAccount: String? = null

    @JvmField
    @JsonProperty("BankName")
    var bankName: String? = null

    @JsonProperty("Contacts")
    @JvmField
    @JacksonXmlElementWrapper(useWrapping = false)
    var contacts: Contact? = null
}

