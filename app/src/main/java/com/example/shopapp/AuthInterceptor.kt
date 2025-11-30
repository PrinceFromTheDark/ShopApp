package com.example.shopapp

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val myApplication = context as MainActivity
        val sessionManager = myApplication.sessionManager

//        val sharedPreferences = context.getSharedPreferences("authToken", Context.MODE_PRIVATE)
//        val token = sharedPreferences.getString("authToken", null)

        val token = sessionManager.authToken

        val originalRequest = chain.request()
        val requestWithAuth = if (!token.isNullOrBlank()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest // без токена — пусть сервер вернёт 401
        }

        return chain.proceed(requestWithAuth)
    }
}