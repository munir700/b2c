package co.yap.networking.transactions.requestdtos

data class RMTTransactionRequestDTO(
    var amount: Double?,
    var currency: String?,
    var purposeCode: String?,
    var beneficiaryId: String?,
    var remarks: String?,
    var purposeReason: String?

)