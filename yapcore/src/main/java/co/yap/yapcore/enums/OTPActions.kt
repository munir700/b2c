package co.yap.yapcore.enums

enum class OTPActions {
    // add beneficiary otp actions
    SWIFT_BENEFICIARY,
    RMT_BENEFICIARY,
    CASHPAYOUT_BENEFICIARY,
    DOMESTIC_BENEFICIARY,
    IS_NEW_BENEFICIARY,

    // transfer fund otp actions
    SWIFT,
    UAEFTS,
    RMT,
    CASHPAYOUT,
    DOMESTIC,
    INTERNAL_TRANSFER,
    DOMESTIC_TRANSFER,
    Y2Y
}