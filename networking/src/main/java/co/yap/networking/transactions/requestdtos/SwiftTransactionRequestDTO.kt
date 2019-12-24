package co.yap.networking.transactions.requestdtos

data class SwiftTransactionRequestDTO(
    var beneficiaryId: String?,
    var amount: Double?,
    var settlementAmount: Double?,
    var purposeCode: String?,
    var purposeReason: String?,
    var remarks: String?,
    var fxRate: String?
) {
}