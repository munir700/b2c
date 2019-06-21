package co.yap.networking

import co.yap.networking.interfaces.IRepository
import co.yap.networking.models.ApiError
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import retrofit2.Response

open class BaseRepository: IRepository {

    override suspend fun <T : ApiResponse> executeSafely(call: suspend () -> Response<T>): RetroApiResponse<T> {
        val response = call.invoke()
        if (response.isSuccessful) {
            // TODO: Check if this is not a server side error (4** or 5**) then return error instead of success
            return RetroApiResponse.Success(response.body()!!)
        }

        // Most probably it is a network/internet error
        return RetroApiResponse.Error(ApiError(response.code(), response.errorBody().toString()))
    }
}