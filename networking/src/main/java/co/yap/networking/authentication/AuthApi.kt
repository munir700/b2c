package co.yap.networking.authentication

import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface AuthApi {
    suspend fun getCSRFToken(): RetroApiResponse<ApiResponse>
    suspend fun refreshJWTToken(token: String): RetroApiResponse<LoginResponse>
    suspend fun login(username: String, password: String): RetroApiResponse<LoginResponse>
    suspend fun logout(uuid: String): RetroApiResponse<ApiResponse>
    fun getJwtToken(): String?
    fun setJwtToken(token: String?)
}