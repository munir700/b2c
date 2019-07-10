package co.yap.networking.authentication

import co.yap.networking.BaseRepository
import co.yap.networking.CookiesManager
import co.yap.networking.MALFORMED_JSON_EXCEPTION_CODE
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.requestdtos.CreateOtpRequest
import co.yap.networking.authentication.requestdtos.DemographicDataRequest
import co.yap.networking.authentication.requestdtos.VerifyOtpRequest
import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.authentication.responsedtos.ValidateDeviceResponse
import co.yap.networking.authentication.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object AuthRepository : BaseRepository(), AuthApi {

    // Security URLS
    const val URL_GET_CSRF_TOKEN = "/auth/login"
    const val URL_GET_JWT_TOKEN = "/auth/oauth/oidc/token"
    const val URL_REFRESH_JWT_TOKEN = "/auth/oauth/oidc/token"
    const val URL_LOGOUT = "/auth/oauth/oidc/logout"
    const val URL_POST_DEMOGRAPHIC_DATA = "/customers/api/demographics/"
    const val URL_VALIDATE_DEMOGRAPHIC_DATA = "customers/api/demographics/validate/user-device/{device_id}"
    const val URL_CREATE_OTP = "/messages/api/otp"
    const val URL_VERIFY_OTP = "/messages/api/otp"
    const val URL_VERIFY_USERNAME = "/admin/api/verify-user"

    private val api: AuthRetroService = RetroNetwork.createService(AuthRetroService::class.java)

    override suspend fun login(username: String, password: String): RetroApiResponse<LoginResponse> {
        val response = executeSafely(call = { api.login("client_credentials", username, password) })
        when (response) {
            is RetroApiResponse.Success -> CookiesManager.jwtToken = response.data.accessToken
        }
        return response
    }


    override suspend fun getCSRFToken(): RetroApiResponse<ApiResponse> {
        val response: RetroApiResponse<ApiResponse> = executeSafely(call = { api.getCSRFToken() })
        when (response) {
            is RetroApiResponse.Error -> {
                if (response.error.statusCode == MALFORMED_JSON_EXCEPTION_CODE) {
                    // this is expected response so mark it a success
                    return RetroApiResponse.Success(200, ApiResponse())
                }
            }
        }

        return response

    }

    override suspend fun refreshJWTToken(token: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.refreshJWTToken("refresh", token) })

    override suspend fun logout(): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.logout() })


    override suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.postDemographicData(demographicDataRequest) })

    override suspend fun validateDemographicData(deviceId: String): RetroApiResponse<ValidateDeviceResponse> =
        executeSafely(call = { api.validateDemographicData(deviceId) })


    override suspend fun createOtp(createOtpRequest: CreateOtpRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.createOtp(createOtpRequest) })

    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): RetroApiResponse<ValidateDeviceResponse> =
        executeSafely(call = { api.verifyOtp(verifyOtpRequest) })

    override suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse> =
        executeSafely(call = { api.verifyUsername(username) })

}
