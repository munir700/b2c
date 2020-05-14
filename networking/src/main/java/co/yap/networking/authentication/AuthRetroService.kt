package co.yap.networking.authentication

import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthRetroService {

    // Get CSRF Token
    @GET(AuthRepository.URL_GET_CSRF_TOKEN)
    suspend fun getCSRFToken(): Response<ApiResponse>

    // Refresh JWT Token
    @POST(AuthRepository.URL_REFRESH_JWT_TOKEN)
    suspend fun refreshJWTToken(
        @Query("grant_type") grantType: String,
        @Query("id_token") token: String
    ): Response<LoginResponse>

    @POST(AuthRepository.URL_GET_JWT_TOKEN)
    suspend fun login(
        @Query("grant_type") grantType: String, @Query("client_id") username: String, @Query(
            "client_secret"
        ) password: String
    ): Response<LoginResponse>

    // Logout
    @POST(AuthRepository.URL_LOGOUT)
    suspend fun logout(@Query("uuid") uuid: String): Response<ApiResponse>

    // Switch Profile
    @FormUrlEncoded
    @POST(AuthRepository.URL_SWITCH_PROFILE)
    suspend fun switchProfile(@Field("account_uuid") uuid: String): Response<LoginResponse>

    //getJwtToken
    suspend fun getJwtToken(): String?

    //setJwtToken
    suspend fun setJwtToken(token: String?)

}