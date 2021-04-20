package co.yap.billpayments.dashboard.mybills.adapter

data class MyBillModel(
    var creationDate: String?,
    var billerName: String?,
    var amount: String?,
    var currency: String?,
    var nickName: String?,
    var logo: String?,
    var billStatus: String?,
    var dueDate: String?
)
