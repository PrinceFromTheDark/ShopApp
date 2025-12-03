package com.example.shopapp.dto

import androidx.compose.ui.window.application
import com.example.shopapp.MainActivity
import com.example.shopapp.MainApplication
import com.example.shopapp.SessionManager
import kotlinx.serialization.Serializable

@Serializable
data class GameDTO(
    val id: Long,
    val title: String,
    val description: String,
    val price: Float,
    val genre: String,
    val developerId: Long,
    val liked: Boolean,
    val likes: Long,
    val logo: String?,
) {

    val isInCart: Boolean
        get() {
            val appContext = MainApplication.appContext as MainActivity
            val sessionManager = appContext.sessionManager
            return sessionManager.cartItems.firstOrNull { it.game.id == this.id} != null
        }
    init {
        require(title.isNotBlank() && title.length <= 256) { "Title cannot be blank or larger than 256 symbols" }
        require(genre.length <= 256) { "Genre name must be under 256 symbols" }

    }
}
