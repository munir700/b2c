package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillModel(
    @SerializedName("logoUrl")
    var logoUrl: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("currency")
    var currency: String?,
    @SerializedName("amount")
    var amount: String?,
    @SerializedName("billStatus")
    var billStatus: String?,
    @Transient
    var isSelected: Boolean = false
)
