package com.example.shopapp.dto

data class OrderRequestDTO(
    val order: OrderCreationDTO,
    val items: OrderItemCreationDTO,
)
