package com.example.shopapp

import com.example.shopapp.dto.GameDTO

data class CartItem(
    val game: GameDTO,
    val quantity: Int
)