package co.yap.networking.missinginfo

import co.yap.networking.missinginfo.requestdtos.GetMissingInfoListRequest
import co.yap.networking.missinginfo.responsedtos.MissingInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface MissingInfoRetroService {

    // Get Missing Info List
    @GET(MissingInfoRepository.URL_GET_LIST)
    suspend fun getMissingInfoList(@Body request: GetMissingInfoListRequest): Response<MissingInfoResponse>
}