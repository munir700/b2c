package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName

data class BillModel(
    @SerializedName("billNickName")
    var billNickName: String?,
    @SerializedName("billDueDate")
    var billDueDate: String?,
    @SerializedName("totalAmountDue")
    var totalAmountDue: String?,
    @SerializedName("billAmountDue")
    var billAmountDue: String?,
    @SerializedName("indicativeFXRate")
    var indicativeFXRate: String?,
    @SerializedName("settlementCurrency")
    var settlementCurrency: String?,
    @SerializedName("baseCurrency")
    var baseCurrency: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("inputsData")
    var inputsData: List<BillerInputData>?,
    @SerializedName("billerInfo")
    var billerInfo: BillerInfoModel?,
    @Transient
    var isSelected: Boolean = false
)
