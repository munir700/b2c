package co.yap.billpayments.mybills.adapter

data class BillModel(
    var logoUrl: String,
    var name: String,
    var description: String,
    var currency: String,
    var amount: String,
    var billStatus: String,
    var isSelected: Boolean = false
)
