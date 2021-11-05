package co.yap.networking.missinginfo

import co.yap.networking.missinginfo.requestdtos.GetMissingInfoListRequest
import co.yap.networking.missinginfo.responsedtos.MissingInfoResponse
import co.yap.networking.models.RetroApiResponse

interface MissingInfoApi {
    suspend fun getMissingInfoList(request: GetMissingInfoListRequest): RetroApiResponse<MissingInfoResponse>
}