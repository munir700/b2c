package co.yap.networking.authentication

import co.yap.networking.BaseRepository
import co.yap.networking.CookiesManager
import co.yap.networking.MALFORMED_JSON_EXCEPTION_CODE
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.responsedtos.LoginResponse
import co.yap.networking.authentication.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object AuthRepository : BaseRepository(), AuthApi {

    // Security URLS
    const val URL_GET_CSRF_TOKEN = "/auth/login"
    const val URL_GET_JWT_TOKEN = "/auth/oauth/oidc/token"
    const val URL_REFRESH_JWT_TOKEN = "/auth/oauth/oidc/token"
    const val URL_LOGOUT = "/auth/oauth/oidc/logout"
    const val URL_VERIFY_USERNAME = "/admin/api/verify-user"

    private val API: AuthRetroService = RetroNetwork.createService(AuthRetroService::class.java)

    override suspend fun login(username: String, password: String): RetroApiResponse<LoginResponse> {
        val response = executeSafely(call = { API.login("client_credentials", username, password) })
        when (response) {
            is RetroApiResponse.Success -> CookiesManager.jwtToken = response.data.accessToken
        }
        return response
    }


    override suspend fun getCSRFToken(): RetroApiResponse<ApiResponse> {
        val response: RetroApiResponse<ApiResponse> = executeSafely(call = { API.getCSRFToken() })
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
        executeSafely(call = { API.refreshJWTToken("refresh", token) })

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

    override suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse> =
        executeSafely(call = { API.verifyUsername(username) })

}
