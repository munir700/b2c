package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillAddedResponse(
    @SerializedName("data")
    var viewBillModel: ViewBillModel? = null
) : ApiResponse()
