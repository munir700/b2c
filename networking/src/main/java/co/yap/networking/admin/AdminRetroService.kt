package co.yap.networking.admin

import co.yap.networking.admin.requestdtos.ForgotPasscodeRequest
import co.yap.networking.admin.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface AdminRetroService {

    // Verify username
    @POST(AdminRepository.URL_VERIFY_USERNAME)
    suspend fun verifyUsername(@Query("username") username: String): Response<VerifyUsernameResponse>

    //Forgot passcode request
    @PUT(AdminRepository.URL_FORGOT_PASSCODE)
    suspend fun forgotPasscode(@Body forgotPasscodeRequest: ForgotPasscodeRequest): Response<ApiResponse>


    //validate current passcode
    @GET(AdminRepository.URL_VALIDATE_CURRENT_PASSCODE)
    suspend fun validateCurrentPasscode(@Query("passcode") passcode: String): Response<ApiResponse>

    //change passcode
    @POST(AdminRepository.URL_CHANGE_PASSCODE)
    suspend fun changePasscode(@Query("new-password") newPasscode: String): Response<ApiResponse>


}