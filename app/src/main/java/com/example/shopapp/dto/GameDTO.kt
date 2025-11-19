package com.example.shopapp.dto

import java.math.BigDecimal

data class GameDTO(
    val id: UInt,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val genre: String,
    val developerId: UInt,
    val liked: Boolean,
    val likes: UInt,
    val logo: String?,
) {
    init {
        require(title.isNotBlank() && title.length <= 256) { "Title cannot be blank or larger than 256 symbols" }
        require(genre.length <= 256) { "Genre name must be under 256 symbols" }

    }
}
