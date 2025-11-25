package com.example.shopapp.interfaces

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class SignUpRequest(
    val nickname: String,
    val password: String,
    val email: String
)

data class SignUpResponse(
    val token: String
)

data class SignInRequest(
    val nickname: String,
    val password: String,
)

data class SignInResponse(
    val token: String
)

interface ApiService {
    @POST("api/Auth/Register")
    suspend fun register(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("api/Auth/Login")
    suspend fun login(@Body request: SignInRequest): Response<SignInResponse>
}