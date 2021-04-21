package co.yap.billpayments.dashboard.home.adapter

data class DueBill(
    var logoUrl: String,
    var billerName: String,
    var billNickName: String,
    var billDueDate: String,
    var amount: String,
    var currency: String
)
