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
            return RetroApiResponse.Error(ApiError(MALFORMED_JSON_EXCEPTION_CODE, exception.localizedMessage))
        } catch (exception: Exception) {
            return RetroApiResponse.Error(ApiError(0, exception.localizedMessage))
        }
    }

    private fun <T : ApiResponse> detectError(response: Response<T>): ApiError {
        if (response.code() == 504) {
            // It is no internet connect error
            // brTODO: take default error message from repo to show here
            return ApiError(response.code(), "")
        }

        // hmm.. may be server error or network error
        val error: String? = response.errorBody()!!.string()
        return ApiError(response.code(), fetchErrorFromBody(error) ?: error ?: "Something went wrong")
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
                            return if (message!="null") {
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
}