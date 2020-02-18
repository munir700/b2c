package co.yap.yapcore.enums

enum class TransactionProductCode(val pCode: String) {
    Y2Y_TRANSFER("P003"),
    WITHDRAW("P004"),
    CARD_REORDER("P005"),
    SUPPLEMENTRY_CARD("P006"),
    TOPUP_BY_CARD("P009"),
    UAEFTS("P010"),
    SWIFT("P011"),
    RMT("P012"),
    CASHPAYOUT("P013"),
    MANUAL_ADJUSTMENT("P014"),
    FEE_DEDUCT("P015"),
    DOMESTIC("P023"),

}