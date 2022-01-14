package co.yap.networking.customers.responsedtos.billpayment

enum class BillStatus(val title: String) {
    OVERDUE("Overdue"),
    BILL_DUE("Bill Due"),
    PAID("Paid"),
    PREPAID("Prepaid"),
    NA("NA")
}
