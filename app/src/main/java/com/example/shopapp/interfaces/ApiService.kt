import retrofit.Response
import retrofit.http.POST
import retrofit.http.Query

interface ApiService {
    @POST("Register")
    suspend fun register(
        @Query("Nickname") nickname: String,
        @Query("Password") password: String,
        @Query("Email") email: String
    ): Response<UserResponse> // или Response<String> если нет модели
}