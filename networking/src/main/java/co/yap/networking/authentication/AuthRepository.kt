package co.yap.networking.authentication

import co.yap.networking.*
import co.yap.networking.CookiesManager
import co.yap.networking.authentication.requestdtos.LoginRequest
import co.yap.networking.authentication.requestdtos.TokenRefreshRequest
import co.yap.networking.authentication.responsedtos.CSRFTokenResponse
import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object AuthRepository : BaseRepository(), AuthApi {

    // Security URLS
    const val URL_GET_CSRF_TOKEN = "/auth/login"
    const val URL_GET_JWT_TOKEN = "/auth/oauth/oidc/login-token"
    const val URL_LOGOUT = "/auth/oauth/oidc/logout"
    const val URL_MS_LOGIN_TOKEN = "/api/notifications/customer-token"
    const val URL_SWITCH_PROFILE = "/auth/oauth/oidc/switch-profile"

    private val API: AuthRetroService = RetroNetwork.createService(AuthRetroService::class.java)

    override suspend fun getCSRFToken(): RetroApiResponse<CSRFTokenResponse> {
        val response: RetroApiResponse<CSRFTokenResponse> = executeSafely(call = { API.getCSRFToken() })
        when (response) {
            is RetroApiResponse.Error -> {
                if (response.error.statusCode == MALFORMED_JSON_EXCEPTION_CODE) {
                    // this is expected response so mark it a success
                    return RetroApiResponse.Success(200, CSRFTokenResponse(MALFORMED_JSON_EXCEPTION_CODE,""))
                } else if (response.error.statusCode == UNKNOWN_HOSE_EXCEPTION_CODE) {
                    return RetroApiResponse.Success(200, CSRFTokenResponse(UNKNOWN_HOSE_EXCEPTION_CODE , "Looks like you're offline. Please reconnect and refresh to continue using YAP."))
                }
            }
        }
        return response
    }

    override suspend fun login(loginRequest: LoginRequest): RetroApiResponse<LoginResponse> {
        val response =
            executeSafely(call = { API.login(loginRequest) })
        when (response) {
            is RetroApiResponse.Success -> {
                CookiesManager.jwtToken = response.data.accessToken
                CookiesManager.isLoggedIn = true
            }
        }
        return response
    }

    override suspend fun refreshJWTToken(tokenRefreshRequest: TokenRefreshRequest): RetroApiResponse<LoginResponse> {
        val response = executeSafely(call = { API.refreshJWTToken(tokenRefreshRequest) })
        when (response) {
            is RetroApiResponse.Success -> {
                CookiesManager.jwtToken = response.data.accessToken
                CookiesManager.isLoggedIn = true
            }
        }
        return response

    }

    override suspend fun logout(uuid: String): RetroApiResponse<ApiResponse> {
        val response = executeSafely(call = { API.logout(uuid) })
        when (response) {
            is RetroApiResponse.Success -> {
                CookiesManager.jwtToken = ""
                CookiesManager.isLoggedIn = false
            }
        }
        return response
    }

    override suspend fun switchProfile(uuid: String): RetroApiResponse<LoginResponse> {
        val response = executeSafely(call = { API.switchProfile(uuid) })
        when (response) {
            is RetroApiResponse.Success -> {
                CookiesManager.jwtToken = response.data.accessToken
                CookiesManager.isLoggedIn = true
            }
        }

        return response
    }

    override fun getJwtToken(): String? {
        return CookiesManager.jwtToken
    }

    override fun setJwtToken(token: String?) {
        CookiesManager.jwtToken = token
    }
}
