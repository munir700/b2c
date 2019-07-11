package co.yap.networking.authentication

import co.yap.networking.authentication.requestdtos.CreateOtpRequest
import co.yap.networking.authentication.requestdtos.DemographicDataRequest
import co.yap.networking.authentication.requestdtos.VerifyOtpRequest
import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.authentication.responsedtos.ValidateDeviceResponse
import co.yap.networking.authentication.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthRetroService {

    // Get CSRF Token
    @GET(AuthRepository.URL_GET_CSRF_TOKEN)
    suspend fun getCSRFToken(): Response<ApiResponse>

    // Refresh JWT Token
    @POST(AuthRepository.URL_REFRESH_JWT_TOKEN)
    suspend fun refreshJWTToken(@Query("grant_type") grantType: String, @Query("id_token") token: String): Response<ApiResponse>

    @POST(AuthRepository.URL_GET_JWT_TOKEN)
    suspend fun login(@Query("grant_type") grantType: String, @Query("client_id") username: String, @Query("client_secret") password: String): Response<LoginResponse>

    // Logout
    @POST(AuthRepository.URL_LOGOUT)
    suspend fun logout(@Query("uuid") uuid: String): Response<ApiResponse>

    // Post demographic data
    @PUT(AuthRepository.URL_POST_DEMOGRAPHIC_DATA)
    suspend fun postDemographicData(@Body demographicDataRequest: DemographicDataRequest): Response<ApiResponse>

    // Validate demographic data
    @GET(AuthRepository.URL_VALIDATE_DEMOGRAPHIC_DATA)
    suspend fun validateDemographicData(@Path("device_id") deviceId: String): Response<ValidateDeviceResponse>

    // Create otp request
    @POST(AuthRepository.URL_CREATE_OTP)
    suspend fun createOtp(@Body createOtpRequest: CreateOtpRequest): Response<ApiResponse>

    // Verify otp request
    @PUT(AuthRepository.URL_VERIFY_OTP)
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<ValidateDeviceResponse>

    // Verify username
    @POST(AuthRepository.URL_VERIFY_USERNAME)
    suspend fun verifyUsername(@Query("username") username: String): Response<VerifyUsernameResponse>

}