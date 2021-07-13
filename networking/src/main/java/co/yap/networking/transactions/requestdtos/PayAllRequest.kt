package co.yap.networking.transactions.requestdtos

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName

data class PayAllRequest(
    @SerializedName("billerID")
    val billerID: String,
    @SerializedName("skuID")
    val skuID: String,
    @SerializedName("billAmount")
    val billAmount: Double? = 0.0,
    @SerializedName("customerBillUuid")
    val customerBillUuid: String,
    @SerializedName("paymentInfo")
    val paymentInfo: String? = null,
    @SerializedName("billerCategory")
    val billerCategory: Int,
    @SerializedName("billerName")
    val billerName: String,
    @SerializedName("billData")
    val billData: List<BillerInputData>?
)