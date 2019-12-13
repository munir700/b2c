package co.yap.yapcore.enums

enum class SendMoneyBeneficiaryType(val type: String) {
    SWIFT("SWIFT"),
    RMT("RMT"),
    CASHPAYOUT("CASHPAYOUT"),
    DOMESTIC("DOMESTIC"),
    INTERNAL_TRANSFER("INTERNAL_TRANSFER"),
    UAEFTS("UAEFTS")
}