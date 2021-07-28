package co.yap.yapcore.enums

enum class BillStatus(val title: String) {
    OVERDUE("Overdue"),
    BILL_DUE("Bill Due"),
    PAID("Paid"),
    PREPAID("Prepaid")
}
