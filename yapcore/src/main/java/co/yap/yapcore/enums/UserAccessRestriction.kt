package co.yap.yapcore.enums

enum class UserAccessRestriction {
    ACCOUNT_INACTIVE,
    OTP_BLOCKED,
    EID_EXPIRED,
    CARD_FREEZE_BY_APP,
    CARD_FREEZE_BY_CSR,
    CARD_HOTLISTED_BY_APP,
    CARD_HOTLISTED_BY_CSR,
    IBAN_BLOCKED_BY_RAK_TOTAL,
    IBAN_BLOCKED_BY_RAK_DEBIT,
    IBAN_BLCOKED_BY_RAK_CREDIT,
    CARD_BLOCKED_BY_MASTER_CARD,
    CARD_BLOCKED_BY_YAP_TOTAL,
    CARD_BLOCKED_BY_YAP_DEBIT,
    CARD_BLOCKED_BY_YAP_CREDIT,
    DEBIT_CARD_PIN_BLOCKED,
    NONE
}