package com.example.shopapp.dto

import java.math.BigDecimal

data class UserSelfDTO (
    val id: UInt,
    val nickname: String,
    val email: String,
    val wallet: BigDecimal,
) {
    init {
        require(nickname.isNotBlank() && nickname.length <= 15) { "Nickname cannot be blank or larger than 256 symbols" }
        require(email.isNotBlank() && email.length <= 256) { "Title cannot be blank or larger than 256 symbols" }
    }
}