package co.yap.networking.transactions.requestdtos

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName


data class PayBillRequest(
    @SerializedName("billerID")
    val billerId: String,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("skuID")
    val skuId: String,
    @SerializedName("billAmount")
    val billAmount: String,
    @SerializedName("customerBillUuid")
    val customerBillUuid: String,
    @SerializedName("paymentInfo")
    val paymentInfo: String? = null,
    @SerializedName("billerCategory")
    val billerCategory: String,
    @SerializedName("biller_name")
    val biller_name: String,
    @SerializedName("billData")
    val billData: List<BillerInputData>?
)