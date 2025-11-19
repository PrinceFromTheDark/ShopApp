package com.example.shopapp.dto

import java.math.BigDecimal

data class OrderItemCreationDTO(
    val gameId: UInt,
    val price: BigDecimal,
    val quantity: BigDecimal,
)