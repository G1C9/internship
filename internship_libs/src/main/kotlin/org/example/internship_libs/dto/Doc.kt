package org.example.internship_libs.dto

import com.fasterxml.jackson.annotation.JsonProperty

class Doc {

    @JvmField
    @JsonProperty("DateAction")
    var dateAction: String? = null

    @JvmField
    @JsonProperty("SenderClient")
    var senderClient: SenderClient? = null

    @JvmField
    @JsonProperty("ReceiverClient")
    var receiverClient: ReceiverClient? = null

    @JvmField
    @JsonProperty("DebitAccount")
    var debitAccount: DebitAccount? = null

    @JsonProperty("Sign")
    var sign: ByteArray? = null
}