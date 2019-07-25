package co.yap.networking.admin

import co.yap.networking.admin.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.RetroApiResponse

interface AdminApi {
    suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse>
}