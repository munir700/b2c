package co.yap.networking.transactions.responsedtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val amount: Int?,
    val category: String?,
    val count: Int?,
    val creationDate: String?,
    val currency: String?,
    val description: String?,
    val fromAccountUUID: String?,
    val fromBalanceAfter: Double?,
    val fromBalanceBefore: Int?,
    val fromCard: String?,
    val fromCustomerId: String?,
    val fromIBAN: String?,
    val fromUserType: String?,
    val initiator: String?,
    val otpVerificationReq: Boolean?,
    val paymentMode: String?,
    val processorRefNumber: String?,
    val productCode: String?,
    val productName: String?,
    val remarks: String?,
    val senderName: String?,
    val status: String?,
    val totalAmount: Int?,
    val transactionId: String?,
    val txnCode: String?,
    val txnType: String?
) : Parcelable