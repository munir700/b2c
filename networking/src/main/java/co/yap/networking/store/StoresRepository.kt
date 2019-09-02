package co.yap.networking.store

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.store.requestdtos.CreateStoreRequest

object StoresRepository : BaseRepository(), StoresApi {

    const val URL_GET_STORES = "/stores/apia/b/c"
    private val API: StoresRetroService = RetroNetwork.createService(StoresRetroService::class.java)

    override suspend fun getYapStores(createStoreRequest: CreateStoreRequest): RetroApiResponse<ApiResponse> =
        MessagesRepository.executeSafely(call = { API.getStores(createStoreRequest) })

}