package co.yap.networking.transactions.responsedtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnalyticsTransaction(
    @SerializedName("fromCard")
    val fromCard: String? = null,
    @SerializedName("fromIBAN")
    val fromIBAN: String? = null,
    @SerializedName("productName")
    val productName: String? = null,
    @SerializedName("txnRefNo")
    val txnRefNo: String? = null,
    @SerializedName("amount")
    val amount: Double? = null,
    @SerializedName("totalAmount")
    val totalAmount: Double? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("txnType")
    val txnType: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("txnState")
    val txnState: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("fromBalanceBefore")
    val fromBalanceBefore: Double? = null,
    @SerializedName("fromBalanceAfter")
    val fromBalanceAfter: Double? = null,
    @SerializedName("merchantName")
    val merchantName: String? = null,
    @SerializedName("merchantCategory")
    val merchantCategory: String? = null,
    @SerializedName("remarks")
    val remarks: String? = null,
    @SerializedName("productCode")
    val productCode: String? = null,
    @SerializedName("initiator")
    val initiator: String? = null,
    @SerializedName("processorRefNumber")
    val processorRefNumber: String? = null,
    @SerializedName("transactionId")
    var transactionId: String? = null,
    @SerializedName("fromCustomerId")
    val fromCustomerId: String? = null,
    @SerializedName("senderMobileNo")
    val senderMobileNo: String? = null,
    @SerializedName("senderEmail")
    val senderEmail: String? = null,
    @SerializedName("senderName")
    val senderName: String? = null,
    @SerializedName("maskedCardNo")
    val maskedCardNo: String? = null,
    @SerializedName("terminalId")
    val terminalId: String? = null,
    @SerializedName("settlementCurrency")
    val settlementCurrency: String? = null,
    @SerializedName("cardHolderBillingCurrency")
    val cardHolderBillingCurrency: Int? = null,
    @SerializedName("cardHolderBillingAmount")
    val cardHolderBillingAmount: Double? = null,
    @SerializedName("settlementAmount")
    val settlementAmount: Double? = null,
    @SerializedName("cardAcceptorLocation")
    val cardAcceptorLocation: String? = null,
    @SerializedName("otpVerificationReq")
    val otpVerificationReq: Boolean? = null,
    @SerializedName("merchantCategoryName")
    var merchantCategoryName: String? = null,
    @SerializedName("createdBy")
    val createdBy: String? = null,
    @SerializedName("creationDate")
    val creationDate: String? = null,
    @SerializedName("processorErrorCode")
    val processorErrorCode: String? = null,
    @SerializedName("paymentMode")
    val paymentMode: String? = null,
    @SerializedName("fromUserType")
    val fromUserType: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("updatedDate")
    val updatedDate: String? = null,
    @SerializedName("updatedBy")
    val updatedBy: String? = null,
    @SerializedName("count")
    val count: String? = null,
    @SerializedName("feeAmount")
    val feeAmount: Double? = null,
    @SerializedName("markUp")
    val markUp: Double? = null,
    @SerializedName("vat")
    val vat: Double? = null,
    @SerializedName("cardType")
    val cardType: String? = null,
    @SerializedName("postedFees")
    var postedFees: Double? = null,
    @SerializedName("cbwsi")
    val cbwsi: Boolean? = null,
    @SerializedName("cbwsiFee")
    val cbwsiFee: Boolean? = null,
    @SerializedName("nonChargeable")
    val nonChargeable: Boolean? = null,
    @SerializedName("timeZone")
    val timeZone: String? = null,
    @SerializedName("bankCBWISCompliant")
    val bankCBWISCompliant: Boolean? = null,
    @SerializedName("skipProductLimits")
    var skipProductLimits: Boolean? = null,
    @SerializedName("currencyDecimalScale")
    val currencyDecimalScale: Int? = null,
    @SerializedName("vatPercentage")
    val vatPercentage: Double? = null,
    @SerializedName("reversal")
    val reversal: Boolean? = null,
    @SerializedName("dispute")
    var dispute: Boolean? = null
) : Parcelable