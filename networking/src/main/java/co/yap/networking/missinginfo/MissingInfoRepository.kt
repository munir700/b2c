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
        data.add("ID Number")
        data.add("First Name")
        data.add("Middle Name")
        data.add("Date of Birth")
        data.add("ID Number")
        data.add("First Name")
        data.add("Middle Name")
        data.add("Date of Birth")
        return RetroApiResponse.Success(200, MissingInfoResponse(data))
    }

}
