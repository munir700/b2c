package co.yap.networking

import co.yap.networking.interfaces.IRepository
import co.yap.networking.models.ApiError
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

const val MALFORMED_JSON_EXCEPTION_CODE = 0

open class BaseRepository : IRepository {

    override suspend fun <T : ApiResponse> executeSafely(call: suspend () -> Response<T>): RetroApiResponse<T> {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return RetroApiResponse.Success(response.code(), response.body()!!)
            }

            // Check if this is not a server side error (4** or 5**) then return error instead of success
            return RetroApiResponse.Error(detectError(response))

        } catch (exception: MalformedJsonException) {
            return RetroApiResponse.Error(ApiError(MALFORMED_JSON_EXCEPTION_CODE, exception.localizedMessage))
        }
    }

    private fun <T : ApiResponse> detectError(response: Response<T>) :ApiError {
        if (response.code() == 400) {
            // get the errors from response
            return ApiError(response.code(), fetchErrorFromBody(response.errorBody()!!.string()))
        }

        // hmm.. may be server error or network error
        return ApiError(response.code(), response.errorBody()!!.string())
    }

    private fun fetchErrorFromBody(response: String): String {
        if (response.isNotBlank()) {
            try {
                val obj = JSONObject(response)
                val errors = obj.getJSONArray("errors")
                if (errors.length() > 0) {
                    return errors.getJSONObject(0).getString("message")
                }

            } catch (e: JSONException) {
                return "Malformed JSON Response from API"
            }
        }

        return "Blank message from server :("
    }
}