package co.yap.networking.transactions.requestdtos

data class Y2YFundsTransferRequest(
    var receiverUUID: String?,
    var beneficiaryName: String?,
    var amount: String?,
    var otpVerificationReq: Boolean?,
    var transactionNote: String?
) {
}