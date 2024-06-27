package org.example.internship_libs.dto

import com.fasterxml.jackson.annotation.JsonProperty

class DebitAccount {

    @JvmField
    @JsonProperty("AccountUID")
    var accountUID: String? = null

    @JvmField
    @JsonProperty("Amount")
    var amount: Double? = null
}