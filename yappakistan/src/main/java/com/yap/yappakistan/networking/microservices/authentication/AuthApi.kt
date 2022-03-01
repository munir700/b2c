package com.yap.yappakistan.networking.microservices.authentication

import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.LoginRequest
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.TokenRefreshRequest
import com.yap.yappakistan.networking.microservices.authentication.responsedtos.LoginResponse

interface AuthApi {
    suspend fun refreshJWTToken(tokenRefreshRequest: TokenRefreshRequest): ApiResponse<LoginResponse>
    suspend fun getCSRFToken(): ApiResponse<BaseApiResponse>
    suspend fun login(loginRequest: LoginRequest?): ApiResponse<LoginResponse>
    suspend fun logout(uuid: String): ApiResponse<BaseApiResponse>
    fun getJwtToken(): String?
    fun setJwtToken(token: String?)
}