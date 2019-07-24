package co.yap.networking.admin

import co.yap.networking.admin.responsedtos.VerifyUsernameResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AdminRetroService {

    // Verify username
    @POST(AdminRepository.URL_VERIFY_USERNAME)
    suspend fun verifyUsername(@Query("username") username: String): Response<VerifyUsernameResponse>

}