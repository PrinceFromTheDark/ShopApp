package com.example.shopapp.dto

import com.example.shopapp.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDTO(
    val id: UInt,
    val userId: UInt,
    val summ: BigDecimal,
    val status: OrderStatus,
    val date: LocalDateTime,

)
