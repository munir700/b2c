package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillModel(
    @SerializedName("LogoUrl")
    var logoUrl: String?,
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Description")
    var description: String?,
    @SerializedName("Currency")
    var currency: String?,
    @SerializedName("Amount")
    var amount: String?,
    @SerializedName("BillStatus")
    var billStatus: String?,
    @SerializedName("DueDate")
    var billDueDate: String?,
    @Transient
    var isSelected: Boolean = false
)
