package co.yap.networking

import co.yap.networking.interfaces.IRepository
import co.yap.networking.models.ApiError
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import com.google.gson.stream.MalformedJsonException as MalformedJsonException1

const val MALFORMED_JSON_EXCEPTION_CODE = 0

abstract class BaseRepository : IRepository {

    override suspend fun <T : ApiResponse> executeSafely(call: suspend () -> Response<T>): RetroApiResponse<T> {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return RetroApiResponse.Success(response.code(), response.body()!!)
            }

            // Check if this is not a server side error (4** or 5**) then return error instead of success
            return RetroApiResponse.Error(detectError(response))

        } catch (exception: MalformedJsonException1) {
            return RetroApiResponse.Error(
                ApiError(
                    MALFORMED_JSON_EXCEPTION_CODE,
                    exception.localizedMessage
                )
            )
        } catch (exception: Exception) {
            return RetroApiResponse.Error(ApiError(0, exception.localizedMessage))
        }
    }

    private fun <T : ApiResponse> detectError(response: Response<T>): ApiError {
        return when (response.code()) {
            403 -> ApiError(response.code(), mapError(NetworkErrors.Forbidden))
            404 -> ApiError(response.code(), mapError(NetworkErrors.NotFound))
            502 -> ApiError(response.code(), mapError(NetworkErrors.BadGateway))
            504 -> ApiError(response.code(), mapError(NetworkErrors.NoInternet))
            in 400..499 -> ApiError(
                response.code(),
                mapError(NetworkErrors.InternalServerError(response.errorBody()?.string()))
            )
            -1009 -> ApiError(response.code(), mapError(NetworkErrors.NoInternet))
            -1001 -> ApiError(response.code(), mapError(NetworkErrors.RequestTimedOut))
            else -> {
                ApiError(response.code(), mapError(NetworkErrors.UnknownError()))
            }
        }
    }
    
    private fun fetchErrorFromBody(response: String?): String? {
        response?.let {
            if (it.isNotBlank()) {
                try {
                    val obj = JSONObject(it)

                    if (obj.has("errors")) {
                        val errors = obj.getJSONArray("errors")
                        if (errors.length() > 0) {
                            val message = errors.getJSONObject(0).getString("message")
                            return if (message != "null") {
                                errors.getJSONObject(0).getString("message")
                            } else {
                                "Something went wrong"
                            }
                        }
                    } else if (obj.has("error")) {
                        // most probably.. unauthorised error
                        val error = obj.getString("error") ?: "Something went wrong"
                        if (error.contains("unauthorized")) {
                            return ""
                        }
                        return error
                    }

                } catch (e: JSONException) {
                    // return "Server sent some malformed address :o"
                }
            }
        }
        return null
    }

    private fun mapError(error: NetworkErrors): String {
        return when (error) {
            is NetworkErrors.NoInternet -> "Internet appears to be offline."
            is NetworkErrors.RequestTimedOut -> "Internet appears to be offline. Please check your internet connection and try again."
            is NetworkErrors.BadGateway -> "Bad Gateway"
            is NetworkErrors.NotFound -> "Resource Not Found"
            is NetworkErrors.Forbidden -> "You don't have access to this information"
            is NetworkErrors.InternalServerError -> fetchErrorFromBody(error.response)
                ?: error.response ?: "Something went wrong"
            is NetworkErrors.UnknownError -> "Something went wrong."
        }
    }
}