package co.yap.networking.transactions.responsedtos.payallbills

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("billAmount")
    val billAmount: Double? = null,
    @SerializedName("billData")
    val billData: List<BillData>? = null,
    @SerializedName("billerCategory")
    val billerCategory: Int? = null,
    @SerializedName("billerID")
    val billerID: String? = null,
    @SerializedName("billerName")
    val billerName: String? = null,
    @SerializedName("customerBillUuid")
    val customerBillUuid: String? = null,
    @SerializedName("paymentInfo")
    val paymentInfo: String? = null,
    @SerializedName("skuID")
    val skuID: String? = null,
    @SerializedName("status")
    val status: String? = null
) : ApiResponse()