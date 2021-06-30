package co.yap.networking.customers.responsedtos.billpayment

import android.os.Parcelable
import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewBillModel(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("billNickName")
    var billNickName: String? = null,
    @SerializedName("billDueDate")
    var billDueDate: String? = null,
    @SerializedName("totalAmountDue")
    var totalAmountDue: String? = null,
    @SerializedName("billAmountDue")
    var billAmountDue: String? = null,
    @SerializedName("indicativeFXRate")
    var indicativeFXRate: String? = null,
    @SerializedName("settlementCurrency")
    var settlementCurrency: String? = null,
    @SerializedName("baseCurrency")
    var baseCurrency: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @SerializedName("billerID")
    var billerID: String? = null,
    @SerializedName("skuId")
    var skuId: String? = null,
    @SerializedName("customerUUID")
    var customerUUID: String? = null,
    @SerializedName("uuid")
    var uuid: String? = null,
    @SerializedName("paymentInfo")
    var paymentInfo: String? = null,
    @SerializedName("autoPayment")
    var autoPayment: Boolean? = false,
    @SerializedName("reminderNotification")
    var reminderNotification: Boolean? = false,
    @SerializedName("inputsData")
    var inputsData: List<BillerInputData>? = null,
    @SerializedName("billerCatalog")
    var billerInfo: BillerInfoModel? = null,
    @Transient
    var formattedDueDate: String? = null
) : Parcelable

//"autoPayment": null,
//"reminderNotification": null,