package co.yap.networking.transactions.requestdtos

data class CashPayoutRequestDTO(
    var purposeCode: String?,
    var beneficiaryId: String?,
    var amount: Double?,
    var currency: String?,
    var transactionNote:String?
) {

}