package co.yap.yapcore.enums

import androidx.annotation.Keep

@Keep
enum class SendMoneyBeneficiaryType(val type: String) {
    SWIFT("SWIFT"), // international
    RMT("RMT"), // international (here, only RMT countries allowed)
    CASHPAYOUT("CASHPAYOUT"),  // Cash payment
    DOMESTIC("DOMESTIC"),  // Within UAE
    INTERNAL_TRANSFER("INTERNAL_TRANSFER"),
    UAEFTS("UAEFTS"),  // Within UAE (YAP to other => here the bank is non RAK bank)
    DOMESTIC_TRANSFER("DOMESTIC_TRANSFER"),  // Within UAE
    YAP2YAP("Y2Y")
}