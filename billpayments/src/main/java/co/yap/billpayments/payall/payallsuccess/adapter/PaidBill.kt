package co.yap.billpayments.payall.payallsuccess.adapter

data class PaidBill(
    var categoryName: String? = null,
    var paymentStatus: String? = null,
    var billerName: String? = null,
    var logo: String? = null,
    var amount: String? = null
)
