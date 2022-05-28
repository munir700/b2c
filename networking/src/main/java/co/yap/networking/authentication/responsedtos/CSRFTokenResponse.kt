package co.yap.networking.authentication.responsedtos

import co.yap.networking.models.ApiResponse

data class CSRFTokenResponse(val code: Int=0, val msg: String?="") : ApiResponse()
