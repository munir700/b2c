package co.yap.yapcore.enums

enum class TransactionProductCode(val pCode: String) {
    Y2Y_TRANSFER("P003"),
    WITHDRAW_SUPPLEMENTARY_CARD("P004"), //  Means withdraw
    CARD_REORDER("P005"),
    TOP_UP_SUPPLEMENTARY_CARD("P006"),
    TOP_UP_VIA_CARD("P009"),
    UAEFTS("P010"),
    SWIFT("P011"),
    RMT("P012"),
    CASH_PAYOUT("P013"),
    MANUAL_ADJUSTMENT("P014"),
    FEE_DEDUCT("P015"),
    DOMESTIC("P023"), // P023 is same as YAP_TO_RAK
    POS("P019"),
    SUPPORT_FEE("P042")

}