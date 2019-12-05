package co.yap.networking.transactions.requestdtos


data class CardTransactionRequest(
    var number: Int,
    var size: Int,
    var serialNumber: String
)
