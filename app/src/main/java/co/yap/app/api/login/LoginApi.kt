package co.yap.app.api.login

import co.yap.app.api.login.requestdtos.LoginRequest
import co.yap.app.api.login.responsedtos.LoginResponse
import co.yap.networking.models.RetroApiResponse

interface LoginApi {
    suspend fun login(request: LoginRequest): RetroApiResponse<LoginResponse>
}