package co.yap.yapcore.enums

enum class BillPaymentStatus(val title: String) {
    PAID("SUCCESS"),
    IN_PROGRESS("IN_PROGRESS"),
    FAILED("FAILED"),
    IN_PROGRESSTITLE("In progress"),
    FAILEDTITLE("Declined"),
    PAIDTITLE("Success"),
}
