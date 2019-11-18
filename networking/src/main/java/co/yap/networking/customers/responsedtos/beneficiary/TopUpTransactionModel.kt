package co.yap.networking.customers.responsedtos.beneficiary

data class TopUpTransactionModel(
    var orderId: String?,
    var currency: String? = "",
    var amount: String? = "",
    var cardId: Int?,
    var secureId: String?
) {
}