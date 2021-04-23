package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillResponse(
    @SerializedName("data")
    var viewBillList: List<ViewBillModel>
)
