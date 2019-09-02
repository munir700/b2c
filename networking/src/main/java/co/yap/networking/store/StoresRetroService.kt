package co.yap.networking.store

import co.yap.networking.models.ApiResponse
import co.yap.networking.store.requestdtos.CreateStoreRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StoresRetroService {

    // Create otp for mobile number
    @POST(StoresRepository.URL_GET_STORES)
    suspend fun getStores(@Body createStoreRequest: CreateStoreRequest): Response<ApiResponse>
}