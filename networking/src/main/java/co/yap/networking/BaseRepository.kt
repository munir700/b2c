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
            403 -> getApiError(mapError(NetworkErrors.Forbidden, response.code()))
            404 -> getApiError(mapError(NetworkErrors.NotFound, response.code()))
            502 -> getApiError(mapError(NetworkErrors.BadGateway, response.code()))
            504 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            in 400..500 -> getApiError(
                mapError(
                    NetworkErrors.InternalServerError(response.errorBody()?.string()),
                    response.code()
                )
            )
            -1009 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            -1001 -> getApiError(mapError(NetworkErrors.RequestTimedOut, response.code()))
            else -> {
                getApiError(mapError(NetworkErrors.UnknownError(), response.code()))
            }
        }
    }

    private fun fetchErrorFromBody(code: Int, response: String?): ServerError {
        response?.let {
            if (it.isNotBlank()) {
                try {
                    val obj = JSONObject(it)

                    if (obj.has("errors")) {
                        val errors = obj.getJSONArray("errors")
                        if (errors.length() > 0) {
                            val message = errors.getJSONObject(0).getString("message")
                            val actualCode = errors.getJSONObject(0).getString("code")
                            return if (message != "null") {
                                ServerError(
                                    code,
                                    errors.getJSONObject(0).getString("message"),
                                    actualCode
                                )
                            } else {
                                ServerError(code, "Something went wrong", actualCode)
                            }
                        }
                    } else if (obj.has("error")) {
                        // most probably.. unauthorised error
                        val error = obj.getString("error") ?: "Something went wrong"
                        if (error.contains("unauthorized")) {
                            return ServerError(0, "")
                        }
                        return ServerError(0, error)
                    }
                } catch (e: JSONException) {
                    ServerError(code, "Something went wrong")
                }
            }
        }
        return ServerError(0, "")
    }

    private fun getApiError(error: ServerError): ApiError {
        return ApiError(
            error.code ?: getDefaultCode(),
            error.message ?: getDefaultMessage(),
            error.actualCode
        )
    }

    private fun getDefaultMessage(): String {
        return "Something went wrong."
    }

    private fun getDefaultCode(): Int {
        return 0
    }

    private fun mapError(error: NetworkErrors, code: Int = 0): ServerError {
        return when (error) {

            is NetworkErrors.NoInternet -> ServerError(
                code,
                "Looks like you're offline. Please reconnect and refresh to continue using YAP."
            )
            is NetworkErrors.RequestTimedOut -> ServerError(
                code,
                "Looks like you're offline. Please reconnect and refresh to continue using YAP."
            )
            is NetworkErrors.BadGateway -> ServerError(code, "Bad Gateway")
            is NetworkErrors.NotFound -> ServerError(code, "Resource Not Found")
            is NetworkErrors.Forbidden -> ServerError(
                code,
                "You don't have access to this information"
            )
            is NetworkErrors.InternalServerError -> fetchErrorFromBody(code, error.response)
            is NetworkErrors.UnknownError -> ServerError(code, getDefaultMessage())
        }
    }

    data class ServerError(val code: Int?, val message: String?, val actualCode: String = "-1")
}