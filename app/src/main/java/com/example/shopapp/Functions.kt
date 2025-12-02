package com.example.shopapp

import android.graphics.BitmapFactory
import android.util.Base64
import com.example.shopapp.dto.GameDTO

private lateinit var sessionManager: SessionManager

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

fun addItemToCart(sessionManager: SessionManager, item: GameDTO) {
    val list = sessionManager.itemsInCart
    list.add(item)

    sessionManager.itemsInCart = list
}