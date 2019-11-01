package co.yap.networking.transactions.responsedtos

data class TransactionDetails(
    val title: String?,
    val fromCard: String?,
    val productName: String?,
    val category: String?,
    val txnType: String?,
    val amount: Double?,
    val totalAmount: Double?,
    val currency: String?,
    val status: String?,
    val fromBalanceBefore: Double?,
    val fromBalanceAfter: Double?,
    val productCode: String?,
    val fromAccountUUID: String?,
    val initiator: String?,
    val transactionId: String?,
    val fromCustomerId: String?,
    val otpVerificationReq: Boolean?,
    val creationDate: String?,
    val paymentMode: String?,
    val count: Int?,
    val fee: Double?,
    val transactionNote: String?
)

