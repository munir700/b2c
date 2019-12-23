package co.yap.networking.transactions.requestdtos

data class CashPayoutRequestDTO(
    var amount: Double?,
    var currency: String?,
    var purposeCode: String?,
    var beneficiaryId: Int?,
    var transactionNote:String?
)