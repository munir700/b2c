package co.yap.app.api.login

import co.yap.app.api.login.requestdtos.LoginRequest
import co.yap.app.api.login.responsedtos.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginRetroApi {

    @GET("someurl/login")
    fun login(@Query("login") request: LoginRequest): Deferred<Response<LoginResponse>>
}