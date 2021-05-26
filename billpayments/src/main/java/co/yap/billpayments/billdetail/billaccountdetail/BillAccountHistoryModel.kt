package co.yap.billpayments.billdetail.billaccountdetail

data class BillAccountHistoryModel(
    var lastPaymentMonth: String?,
    var lastPaymentAmount: String?,
    var totalPayment: String?,
    var highestMonth: String?,
    var highestAmount: String?,
    var lowestMonth: String,
    var lowestAmount: String
)
