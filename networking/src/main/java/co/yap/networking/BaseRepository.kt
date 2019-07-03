package co.yap.networking

import co.yap.networking.interfaces.IRepository
import co.yap.networking.models.ApiError
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import com.google.gson.stream.MalformedJsonException
import retrofit2.Response

const val MALFORMED_JSON_EXCEPTION_CODE = 0

open class BaseRepository : IRepository {

    override suspend fun <T : ApiResponse> executeSafely(call: suspend () -> Response<T>): RetroApiResponse<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                // TODO: Check if this is not a server side error (4** or 5**) then return error instead of success
                return RetroApiResponse.Success(response.code(), response.body()!!)
            }

            // Most probably it is a network/internet error
            return RetroApiResponse.Error(ApiError(response.code(), response.errorBody().toString()))
        } catch (exception: MalformedJsonException) {
            return RetroApiResponse.Error(ApiError(MALFORMED_JSON_EXCEPTION_CODE, exception.localizedMessage))
        }
    }
}