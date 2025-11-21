package com.example.shopapp.common


import com.example.shopapp.interfaces.ApiService
import com.example.shopapp.retrofit.RetrofitClient

object Common {
    private val BASE_URL = "https://www.simplifiedcoding.net/demos/"
    val apiService: ApiService
        get() = RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
}