package co.yap.yapcore.enums

enum class OTPActions {
    FORGOT_CARD_PIN,
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
    DOMESTIC_TRANSFER,
    INTERNAL_TRANSFER,
    Y2Y
}