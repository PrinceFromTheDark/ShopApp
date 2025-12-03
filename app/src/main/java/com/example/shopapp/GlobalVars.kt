package com.example.shopapp

object GlobalVars {
    val pageSize = 8
    var userId = -1L

    var cartItems: ArrayList<CartItem>
        get() {
            return GlobalVars.cartItems.firstOrNull { it.game.id == this.userId} != null
        }
}