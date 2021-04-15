package co.yap.billpayments.dashboard.billaccountdetail

data class BillAccountDetailModel(
    var logo: String?,
    var billerName: String?,
    var nickName: String?,
    var currency: String?,
    var dueAmount: String?,
    var billStatus: String?,
    var lastPayment: String?,
    var totalPayment: String?,
    var highestMonth: String?,
    var lowestMonth: String
)
