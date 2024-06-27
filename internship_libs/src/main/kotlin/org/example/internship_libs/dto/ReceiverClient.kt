package org.example.internship_libs.dto

import com.fasterxml.jackson.annotation.JsonProperty

class ReceiverClient {

    @JvmField
    @JsonProperty("ShortName")
    var shortName: String? = null

    @JvmField
    @JsonProperty("ClientUID")
    var clientUID: String? = null

}