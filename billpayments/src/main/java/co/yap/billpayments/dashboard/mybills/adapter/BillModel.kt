package co.yap.billpayments.dashboard.mybills.adapter

data class BillModel(
    var creationDate: String? = null,
    var billerName: String? = null,
    var amount: String? = null,
    var currency: String? = null,
    var nickName: String? = null,
    var logo: String? = null,
    var billStatus: String? = null,
    var dueDate: String? = null
)
