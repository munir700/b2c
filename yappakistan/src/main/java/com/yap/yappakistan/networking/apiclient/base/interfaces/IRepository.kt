package com.yap.yappakistan.networking.apiclient.base.interfaces

import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import retrofit2.Response

internal interface IRepository {
    suspend fun <T : BaseApiResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T>
}