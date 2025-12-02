package com.example.shopapp.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameDTO(
    val id: Long,
    val title: String,
    val description: String,
    val price: Float,
    val genre: String,
    val developerId: Long,
    val liked: Boolean,
    val likes: Long,
    val logo: String?,
    val amountInCart: Int,
) {
    init {
        require(title.isNotBlank() && title.length <= 256) { "Title cannot be blank or larger than 256 symbols" }
        require(genre.length <= 256) { "Genre name must be under 256 symbols" }

    }
}
