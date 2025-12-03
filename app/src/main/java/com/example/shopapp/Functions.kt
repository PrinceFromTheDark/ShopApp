package com.example.shopapp

import android.graphics.BitmapFactory
import android.util.Base64
import com.example.shopapp.dto.GameDTO

fun loadBase64Image(base64String: String?): Any? {
    if (!base64String.isNullOrEmpty()) {
        try {
            // Убираем префикс data:image если есть
            val cleanBase64 = base64String.substringAfter(",")

            // Декодируем base64 в байтовый массив
            val imageBytes = Base64.decode(cleanBase64, Base64.DEFAULT)

            // Создаем Bitmap из байтового массива
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            // Устанавливаем изображение в ImageView
            return bitmap

        } catch (e: Exception) {
            e.printStackTrace()
            // Устанавливаем изображение-заглушку при ошибке
            return null
        }
    } else {
        // Если изображение отсутствует, устанавливаем заглушку
        return null
    }
}

fun addToCart(sessionManager: SessionManager, game: GameDTO) {
    val items = sessionManager.cartItems
    val existingItem = items.find { it.game.id == game.id } // ⚠️ предполагается, что у GameDTO есть id

    if (existingItem != null) {
        // Увеличиваем количество
        val index = items.indexOf(existingItem)
        items[index] = existingItem.copy(quantity = existingItem.quantity + 1)
    } else {
        // Добавляем новую запись
        items.add(CartItem(game, 1))
    }

    sessionManager.cartItems = items
}

fun removeFromCart(sessionManager: SessionManager, game: GameDTO): Int? {
    val items = sessionManager.cartItems
    val existingItem = items.find { it.game.id == game.id }

    if (existingItem != null) {
        if (existingItem.quantity > 1) {
            // Уменьшаем количество
            val index = items.indexOf(existingItem)
            items[index] = existingItem.copy(quantity = existingItem.quantity - 1)
        } else {
            // Удаляем из списка
            items.remove(existingItem)
        }

        sessionManager.cartItems = items
    }
    return existingItem?.quantity
}