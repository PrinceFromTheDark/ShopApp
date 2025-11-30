package com.example.shopapp

data class Item(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val price: Float,
    val genre: String = "",
    val developerId: Long = 0,
    val liked: Boolean = false,
    val likes: Long = 0,
    val logo: String
)