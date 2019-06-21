package co.yap.app.api.login

import co.yap.app.api.login.requestdtos.LoginRequest
import co.yap.app.api.login.responsedtos.LoginResponse
import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.models.RetroApiResponse

object LoginRepository : BaseRepository(), LoginApi {
    private val api: LoginRetroApi = RetroNetwork.createService(LoginRetroApi::class.java)

    override suspend fun login(request: LoginRequest): RetroApiResponse<LoginResponse> = executeSafely(call = {api.login(request).await()})
}