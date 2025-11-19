package com.example.shopapp.dto

data class UserOthersDTO (
    val nickname: String,
    val imageBase64: String,
) {
    init {
        require(nickname.isNotBlank() && nickname.length <= 256) { "Nickname cannot be blank or larger than 256 symbols" }
    }
}