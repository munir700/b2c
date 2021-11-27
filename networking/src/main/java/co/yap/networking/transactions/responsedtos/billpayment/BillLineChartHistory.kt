package co.yap.networking.transactions.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillLineChartHistory(
        @SerializedName("amount")
        var amount: Double? = null,
        @SerializedName("month")
        var month: String? = null,
        @SerializedName("billDate")
        var billDate: String? = null
) : ApiResponse()
