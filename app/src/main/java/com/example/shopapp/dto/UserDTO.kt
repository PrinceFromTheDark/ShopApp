package com.example.shopapp.dto

import java.math.BigDecimal

data class UserDTO(
    val id: Long,
    val nickname: String,
    val password: String,
    val email: String,
    val roles: String,
    val wallet: Float,
    val avatar: String?,
) {
    init {
        require(nickname.isNotBlank() && nickname.length <= 15) { "Title cannot be blank or larger than 256 symbols" }
        require(password.isNotBlank() && password.length <= 256) { "Password cannot be blank or larger than 256 symbols" }
        require(email.isNotBlank() && email.length <= 256) { "Email cannot be blank or larger than 256 symbols" }
        require(roles.isNotBlank() && roles.length <= 256) { "Roles cannot be blank or larger than 256 symbols" }
    }
}
