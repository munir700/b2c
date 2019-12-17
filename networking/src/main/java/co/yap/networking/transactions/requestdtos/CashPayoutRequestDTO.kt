package co.yap.networking.transactions.requestdtos

data class CashPayoutRequestDTO(
    var purposeCode: String?,
    var beneficiaryId: String?,
    var amount: String?,
    var currency: String?
) {

}