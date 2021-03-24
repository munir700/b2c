package co.yap.billpayments.mybills.adapter

data class MyBillsModel(
    var iconUrl: String,
    var name: String,
    var description: String,
    var currency: String,
    var amount: String,
    var billStatus: String
)
