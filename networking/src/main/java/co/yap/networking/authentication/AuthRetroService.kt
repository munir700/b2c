package co.yap.networking.authentication

import co.yap.networking.authentication.requestdtos.LoginRequest
import co.yap.networking.authentication.requestdtos.TokenRefreshRequest
import co.yap.networking.authentication.responsedtos.CSRFTokenResponse
import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthRetroService {

    // Get CSRF Token
    @GET(AuthRepository.URL_GET_CSRF_TOKEN)
    suspend fun getCSRFToken(): Response<CSRFTokenResponse>

    // Refresh JWT Token
    @POST(AuthRepository.URL_GET_JWT_TOKEN)
    suspend fun refreshJWTToken(@Body tokenRefreshRequest: TokenRefreshRequest): Response<LoginResponse>

    @POST(AuthRepository.URL_GET_JWT_TOKEN)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // Logout
    @POST(AuthRepository.URL_LOGOUT)
    suspend fun logout(@Query("uuid") uuid: String): Response<ApiResponse>

    //getJwtToken
    suspend fun getJwtToken(): String?

    //setJwtToken
    suspend fun setJwtToken(token: String?)

    // Switch Profile
    @FormUrlEncoded
    @POST(AuthRepository.URL_SWITCH_PROFILE)
    suspend fun switchProfile(@Field("account_uuid") uuid: String): Response<LoginResponse>

}