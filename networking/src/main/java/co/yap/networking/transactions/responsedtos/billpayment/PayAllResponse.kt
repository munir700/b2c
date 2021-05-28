package co.yap.networking.transactions.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class PayAllResponse(
    @SerializedName("data")
    val trxnReceiptList: List<PaidBill>? = null
) : ApiResponse()
