package co.yap.networking.transactions.requestdtos

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName


data class PayBillRequest(
    @SerializedName("billerid")
    val billerId: String,
    @SerializedName("skuid")
    val skuId: String,
    @SerializedName("billamount")
    val billAmount: String,
    @SerializedName("billdata")
    val billInputData: List<BillerInputData>?
)