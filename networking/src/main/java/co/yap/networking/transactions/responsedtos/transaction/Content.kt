package co.yap.networking.transactions.responsedtos.transaction

data class Content(
//    old transactions response

//    var closingBalance: Double,
//    var id: Int,
//    var merchant: Any?,
//    var paymentMode: String,
//    var title: Any?,
//    var txnAmount: Double,
//    var txnCategory: String,
//    var txnCurrency: String,
//    var txnDate: String,
//    var txnType: String

    // new transactions response params

    var accountUuid1: String,
    var amount: Double,
    var balanceAfter: Double,
    var balanceBefore: Double,
    var card1: String,
    var category: String,
    var createdBy: String,
    var creationDate: String,
    var currency: String,
    var customerId1: String,
    var description: String,
    var iban1: String,
    var initiator: String,
    var paymentMode: String,
    var processorRefNumber: String,
    var productCode: String,
    var productName: String,
    var remarks: String,
    var senderName: String,
    var status: String,
    var totalAmount: Double,
    var transactionId: String,
    var txnCode: String,
    var txnType: String,
    var updatedBy: String,
    var updatedDate: String,
    var userType1: String


)