package co.yap.networking.authentication

import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface AuthApi {
    suspend fun getCSRFToken(): RetroApiResponse<ApiResponse>
    suspend fun refreshJWTToken(token: String): RetroApiResponse<ApiResponse>
    suspend fun login(username: String, password: String): RetroApiResponse<LoginResponse>
    suspend fun logout(token: String): RetroApiResponse<ApiResponse>
}