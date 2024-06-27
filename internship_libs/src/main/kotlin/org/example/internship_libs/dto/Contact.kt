package org.example.internship_libs.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

class Contact {
    @JsonProperty("Contact")
    @JacksonXmlElementWrapper(useWrapping = false)
    var contact: List<String>? = null
}