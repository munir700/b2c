package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillResponse(
    @SerializedName("data")
    var viewBillList: List<ViewBillModel>? = null
) : ApiResponse()
