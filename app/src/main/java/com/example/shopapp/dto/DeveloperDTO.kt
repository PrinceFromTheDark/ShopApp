package com.example.shopapp.dto

import java.time.LocalDateTime

data class DeveloperDTO(
    val name: String,
    val country: String,
    val description: String,
    val dateTime: LocalDateTime,

) {
    init {
        require(name.isNotBlank() && name.length < 256) { "Product name cannot be blank or larger than 256 symbols" }
        require(country.length < 256) { "Country name must be under 256 symbols" }

    }
}
