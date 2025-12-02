package com.example.shopapp.interfaces

import com.example.shopapp.GlobalVars
import com.example.shopapp.dto.GameDTO
import com.example.shopapp.dto.UserDTO
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    @SerializedName("result")
    val token: String
)

interface ApiService {
    @POST("api/Auth/Register")
    suspend fun register(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("api/Auth/Login")
    suspend fun login(@Body request: SignInRequest): Response<SignInResponse>

    @GET("api/Auth/ValidateToken")
    suspend fun validateToken(): Response<ResponseBody>

    @GET("api/Users/GetUser")
    suspend fun getUser(): Response<UserDTO>

    @GET("api/Games/GetGames")
    suspend fun getGames(
        @Query("pageSize") pageSize: Int = GlobalVars.pageSize,
        @Query("page") page: Int = 1,
        @Query("genre") genre: String = "Any",
        @Query("userId") userId: Long = GlobalVars.userId,
        @Query("filterType") filterType: String? = null,
        @Query("ascending") ascending: Boolean? = null,
        @Query("title") title: String? = null
    ): Response<List<GameDTO>>
}