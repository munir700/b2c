package co.yap.networking.transactions.requestdtos

data class DomesticTransactionRequestDTO(
    var beneficiaryId: String?,
    var amount: Double?,
    var settlementAmount: Double?,
    var purposeCode: String?,
    var purposeReason: String?,
    var transactionNote: String?
) {
}