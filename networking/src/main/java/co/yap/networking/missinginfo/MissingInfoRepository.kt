package co.yap.networking.missinginfo

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.missinginfo.requestdtos.GetMissingInfoListRequest
import co.yap.networking.missinginfo.responsedtos.MissingInfoResponse
import co.yap.networking.models.RetroApiResponse

object MissingInfoRepository : BaseRepository(), MissingInfoApi {
    const val URL_GET_LIST = "/missinginfo/api/list"
    private val API: MissingInfoRetroService =
        RetroNetwork.createService(MissingInfoRetroService::class.java)

    override suspend fun getMissingInfoList(request: GetMissingInfoListRequest): RetroApiResponse<MissingInfoResponse> {
        //executeSafely(call = { API.getMissingInfoList(request) })
        val data = ArrayList<String>()
        for (i in 0..5) {
            data.add("Missing Item $i")
        }
        return RetroApiResponse.Success(200, MissingInfoResponse(data))
    }

}
