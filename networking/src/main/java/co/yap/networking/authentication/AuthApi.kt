package co.yap.networking.authentication

import co.yap.networking.authentication.requestdtos.CreateOtpRequest
import co.yap.networking.authentication.requestdtos.DemographicDataRequest
import co.yap.networking.authentication.requestdtos.VerifyOtpRequest
import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.authentication.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface AuthApi {
    suspend fun getCSRFToken(): RetroApiResponse<ApiResponse>
    suspend fun refreshJWTToken(token: String): RetroApiResponse<ApiResponse>
    suspend fun login(username: String, password: String): RetroApiResponse<LoginResponse>
    suspend fun logout(uuid: String): RetroApiResponse<ApiResponse>
    suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse>
    suspend fun validateDemographicData(deviceId: String): RetroApiResponse<ApiResponse>
    suspend fun createOtp(createOtpRequest: CreateOtpRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse>
}