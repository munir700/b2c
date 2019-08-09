package co.yap.networking.admin

import co.yap.networking.admin.requestdtos.ForgotPasscodeRequest
import co.yap.networking.admin.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AdminRetroService {

    // Verify username
    @POST(AdminRepository.URL_VERIFY_USERNAME)
    suspend fun verifyUsername(@Query("username") username: String): Response<VerifyUsernameResponse>

    //Forgot passcode request
    @PUT(AdminRepository.URL_FORGOT_PASSCODE)
    suspend fun forgotPasscode(@Body forgotPasscodeRequest: ForgotPasscodeRequest):Response<ApiResponse>
}