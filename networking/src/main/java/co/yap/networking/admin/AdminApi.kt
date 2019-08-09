package co.yap.networking.admin

import co.yap.networking.admin.requestdtos.ForgotPasscodeRequest
import co.yap.networking.admin.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface AdminApi {
    suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse>
    suspend fun forgotPasscode(forgotPasscodeRequest: ForgotPasscodeRequest): RetroApiResponse<ApiResponse>
}