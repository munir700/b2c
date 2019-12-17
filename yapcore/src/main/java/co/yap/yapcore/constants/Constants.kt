package co.yap.yapcore.constants

object Constants {
    const val addCard = "addCard"
    const val isPinCreated = "isPinCreated"

    const val TYPE_ADD_FUNDS: String = "addFunds"
    const val TYPE_REMOVE_FUNDS: String = "removeFunds"
    const val CHANGE_MOBILE_NO: String = "CHANGE_MOBILE_NO"
    const val CHANGE_EMAIL: String = "CHANGE_EMAIL"
    const val CHANGE_PASSCODE: String = "CHANGE_PASSCODE"
    const val TRANSACTION_ID: String = "TRANSACTION_ID"
    const val CARD_SERIAL_NUMBER: String = "CARD_SERIAL_NUMBER"
    const val FORGOT_PASSCODE_FROM_CHANGE_PASSCODE: String = "forgotPasscodeFromChangePasscodeFlow"
    const val TRANSACTION_TYPE_CREDIT: String = "CREDIT"
    const val FORGOT_CARD_PIN_FLOW: String = "FORGOT_CARD_PIN_FLOW"
    const val FORGOT_CARD_PIN_ACTION: String = "FORGOT_CARD_PIN"
    const val BENEFICIARY_CASH_TRANSFER: String = "CASHPAYOUT"

    const val TOP_UP: String = "TOP_UP"
    const val CARD: String = "CARD"
    const val KEY: String = "Key"
    const val TYPE: String = "type"
    const val TYPE_ADD_CARD: String = "TYPE_ADD_CARD"
    const val TYPE_TOP_UP_TRANSACTION: String = "TYPE_TOP_UP_TRANSACTION"
    const val START_POOLING: String = "START_POOLING"
    const val TOP_UP_VIA_EXTERNAL_CARD: String = "TOP_UP_VIA_CARD"


    //Transaction Category Constant
    const val Y_TO_Y_TRANSFER = "P003"
    const val SUPP_CARD_TOP_UP = "P004"
    const val SUPP_WITHDRAW = "P006"
    const val MANUAL_DEBIT = "DEBIT"
    const val MANUAL_CREDIT = "CREDIT"


    //More Option Constants
    const val MORE_NOTIFICATION: Int = 1
    const val MORE_LOCATE_ATM: Int = 2
    const val MORE_INVITE_FRIEND: Int = 3
    const val MORE_HELP_SUPPORT: Int = 4
    const val EVENT_CREATE_CARD_PIN: Int = 13
    //Add Note flow constants
    const val INTENT_ADD_NOTE_REQUEST = 2222
    const val KEY_NOTE_VALUE = "noteValue"

    //Product codes
    const val TOP_UP_VIA_CARD: String = "P009"

    // Invite Friend Constants
    const val URL_SHARE_APP_STORE = "itms-apps://itunes.apple.com/app/id1024941703"
    const val URL_SHARE_PLAY_STORE = "https://play.google.com/store/apps/details?id=co.yap"

    const val MODE_STATUS_SCREEN: Int = 1
    const val MODE_HELP_SUPPORT: Int = 2
    const val FORGOT_CARD_PIN_NAVIGATION: Int = 1

    const val DUMMY_CARD: Int = 22
    const val CARD_FEE: Int = 23
    const val TOP_UP_TRANSACTION_SUCCESS: Int = 6

    //CardAnalytics constants
    const val CATEGORY_AVERAGE_AMOUNT_VALUE: Int = 7
    const val MERCHANT_AVERAGE_AMOUNT_VALUE: Int = 8



    //Add Card Payment gateway URL
    const val URL_ADD_TOPUP_CARD = "https://dev.yap.co/admin-web/HostedSessionIntegration.html"

    //HTML Key
    const val URL_TOP_UP_TRANSACTION_HTML = "URL_TOP_UP_TRANSACTION_HTML"

    //Other events
    const val EVENT_TOP_UP_CARD_TRANSACTION: Int = 5

    // Cash pickup flow constants
    const val BENEFICIARY: String = "Beneficiary"


    const val ADD_CASH_PICK_UP_SUCCESS = 10001
    const val ADD_CASH_PICK_UP_FlOW = 10002
    const val ADD_DOMESTIC_SUCCESS = 10003


    //Core Payment Card Types
    const val VISA = "Visa"
    const val MASTER = "Mastercard"
    const val JCB = "JCB"
    const val DINNERS = "Diners Club"
    const val AMEX = "American Express"
    const val DISCOVER = "Discover"
    const val UATP = "UATP"

    const val USER_STATUS_CARD_ACTIVATED: String = "CARD_ACTIVATED"

    const val BROADCAST_UPDATE_TRANSACTION: String = "BROADCAST_UPDATE_TRANSACTION"
}