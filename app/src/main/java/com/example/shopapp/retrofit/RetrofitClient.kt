package com.example.shopapp.retrofit

import android.content.Context
import com.example.shopapp.AuthInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String, context: Context): Retrofit {
        if (retrofit == null) {
            // Создаём логгер
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY  // ← ЛОГИРУЕМ ЗАГОЛОВКИ + ТЕЛО
            }

            // Создаём клиент с логгером

            val authInterceptor = AuthInterceptor(context)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}