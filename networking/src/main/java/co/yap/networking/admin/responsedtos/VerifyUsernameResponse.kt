package co.yap.networking.admin.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class VerifyUsernameResponse(@SerializedName("data") var data: Boolean) : ApiResponse()