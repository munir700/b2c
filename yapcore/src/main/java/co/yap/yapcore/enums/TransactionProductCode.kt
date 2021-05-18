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
    FUND_LOAD("P016"),
    FUND_WITHDRAWL("P017"),
    ATM_WITHDRAWL("P018"),
    POS_PURCHASE("P019"),
    FSS_FUNDS_WITHDRAWAL("P020"),
    VIRTUAL_ISSUANCE_FEE("P021"),
    PHYSICAL_ISSUANCE_FEE("P022"),
    DOMESTIC("P023"),
    CASH_DEPOSIT_AT_RAK("P024"),
    CHEQUE_DEPOSIT_AT_RAK("P025"),
    INWARD_REMITTANCE("P026"),
    LOCAL_INWARD_TRANSFER("P027"),
    FUNDS_WITHDRAWAL_BY_CHEQUE("P028"),
    MASTER_CARD_ATM_WITHDRAWAL("P029"),
    REVERSAL_MASTER_CARD("P030"),
    REFUND_MASTER_CARD("P031"),
    REVERSAL_OF_TXN_ON_FAILURE("P032"),
    CASH_ADVANCE("P033"),
    ATM_DEPOSIT("P034"),
    MOTO("P035"),
    ECOM("P036"),
    BALANCE_INQUIRY("P037"),
    MINISTATEMENT("P038"),
    PIN_CHANGE("P039"),
    ACCOUNT_STATUS_INQUIRY("P040"),
    PAYMENT_TRANSACTION("P041"),
    FSS_FEE_NOTIFICATION("P042");

}

enum class TransactionStatus {
    PENDING, IN_PROGRESS, COMPLETED, FAILED, CANCELLED, NONE
}

enum class TransactionProductType {
    IS_TRANSACTION_FEE, IS_BANK, IS_REFUND, IS_CASH, IS_INCOMING, IS_SEND_MONEY
}

enum class TransactionState {
    FSS_START, FSS_NOTIFICATION_PENDING, RAK_CUT_OFF_TIME_HOLD, FSS_TIMEOUT, FSS_REVERSAL_PENDING
}