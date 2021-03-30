package co.yap.billpayments.paybills.adapter

data class DueBill(
    var logoUrl: String,
    var billerName: String,
    var billNickName: String,
    var billDueDate: String,
    var amount: String,
    var currency: String
)
