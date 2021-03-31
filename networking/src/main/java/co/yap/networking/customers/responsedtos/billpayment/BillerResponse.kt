package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class BillerResponse(@SerializedName("data") val data: List<BillerModel>) : ApiResponse()
