package com.example.shopapp.interfaces

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

data class RegisterRequest(
    val Nickname: String,
    val Password: String,
    val Email: String
)

data class RegisterResponse(
    val Id: Int,
    val Nickname: String,
    val Email: String,
    val Roles: String,
    val Wallet: Int
)

interface ApiService {
    @POST("Register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}