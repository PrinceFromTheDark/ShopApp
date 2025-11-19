package com.example.shopapp.dto

import java.math.BigDecimal

data class OrderItemDTO(
    val gameId: UInt,
    val title: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val logo: String?,
) {
    init {
        require(title.isNotBlank() && title.length < 256) { "Title cannot be blank or larger than 256 symbols" }
    }
}
