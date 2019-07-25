package co.yap.networking.admin

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.admin.responsedtos.VerifyUsernameResponse
import co.yap.networking.models.RetroApiResponse

object AdminRepository : BaseRepository(), AdminApi {


    const val URL_VERIFY_USERNAME = "/admin/api/verify-user"

    private val API: AdminRetroService = RetroNetwork.createService(AdminRetroService::class.java)

    override suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse> =
        executeSafely(call = { API.verifyUsername(username) })

}
