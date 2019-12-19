package co.yap.networking.transactions

data class RMTTransactionRequestDTO(
    var purposeCode: String?,
    var beneficiaryId: String?,
    var amount: Double?,
    var currency: String?,
    var transactionNote:String?
)