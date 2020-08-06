package co.yap.networking.messages.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class OtpValidationResponse(@SerializedName("data") var data: OtpValidation? = OtpValidation()) :
    ApiResponse()