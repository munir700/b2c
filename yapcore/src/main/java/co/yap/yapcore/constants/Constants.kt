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

    const val LONGITUDE = "longitude"
    const val LATITUDE = "latitude"
    const val SUCCESS_RESULT = "SUCCESS_RESULT"
    const val LOCATION_ADDRESS = "LOCATION_ADDRESS"
    const val ERROR_MESSAGE = "ERROR_MESSAGE"

    const val TOP_UP: String = "TOP_UP"
    const val CARD: String = "CARD"
    const val KEY: String = "Key"
    const val TYPE: String = "type"
    const val TYPE_ADD_CARD: String = "TYPE_ADD_CARD"
    const val TYPE_TOP_UP_TRANSACTION: String = "TYPE_TOP_UP_TRANSACTION"
    const val START_POOLING: String = "START_POOLING"
    const val TOP_UP_VIA_EXTERNAL_CARD: String = "TOP_UP_VIA_CARD"

    const val THEME_YAP: String = "CORE"
    const val THEME_HOUSEHOLD: String = "HOUSEHOLD"

    //Transaction Category Constant
    const val Y_TO_Y_TRANSFER = "P003"
    const val SUPP_CARD_TOP_UP = "P004"
    const val SUPP_WITHDRAW = "P006"
    //This is intentional, so don't remove below line
    const val SUPP_CARD = SUPP_WITHDRAW
    const val ADD_FUNDS = SUPP_WITHDRAW
    const val MANUAL_DEBIT = "DEBIT"
    const val MANUAL_CREDIT = "CREDIT"
    const val FEE_TYPE_TIER = "TIER"
    const val FEE_TYPE_FLAT = "FLAT"


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
    const val MODE_MEETING_CONFORMATION: Int = 12
    const val FORGOT_CARD_PIN_NAVIGATION: Int = 1

    const val DUMMY_CARD: Int = 22
    const val CARD_FEE: Int = 23
    const val TOP_UP_TRANSACTION_SUCCESS: Int = 6

    //CardAnalytics constants
    const val CATEGORY_AVERAGE_AMOUNT_VALUE: Int = 7
    const val MERCHANT_AVERAGE_AMOUNT_VALUE: Int = 8


    //Add Card Payment gateway URL
    const val URL_ADD_TOPUP_CARD = "https://dev.yap.co/admin-web/HostedSessionIntegration.html"
    const val URL_TERMS_CONDITION = "https://yap.co/terms"

    //HTML Key
    const val URL_TOP_UP_TRANSACTION_HTML = "URL_TOP_UP_TRANSACTION_HTML"

    //Other events
    const val EVENT_TOP_UP_CARD_TRANSACTION: Int = 5

    // Cash pickup flow constants
    const val BENEFICIARY: String = "Beneficiary"
    const val POSITION: String = "Position"
    const val IS_NEW_BENEFICIARY: String = "IS_NEW_BENEFICIARY"
    const val DOMESTIC_BENEFICIARY: String = "DOMESTIC_BENEFICIARY"
    const val CASHPAYOUT_BENEFICIARY: String = "CASHPAYOUT_BENEFICIARY"
    const val RMT_BENEFICIARY: String = "RMT_BENEFICIARY"
    const val SWIFT_BENEFICIARY: String = "SWIFT_BENEFICIARY"


    const val ADD_CASH_PICK_UP_SUCCESS = 10001
    const val ADD_CASH_PICK_UP_FlOW = 10002
    const val ADD_DOMESTIC_SUCCESS = 10003
    const val ADD_SUCCESS = 10004


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

    // Money Transfer
    const val MONEY_TRANSFERED = "MONEY_TRANSFERED"
    const val BENEFICIARY_CHANGE = "BENEFICIARY_CHANGE"

    //Location Selection
    const val ADDRESS = "address"
    const val ADDRESS_SUCCESS = "address_success"

    // Events for ViewState

    const val EVENT_LOADING: Int = 1111
    const val EVENT_EMPTY: Int = 2222
    const val EVENT_CONTENT: Int = 3333
    const val EVENT_ERROR: Int = 4444

    //Notifications Actions

    const val NOTIFICATION_ACTION_SET_PIN: String = "SET_PIN"
    const val NOTIFICATION_ACTION_COMPLETE_VERIFICATION: String = "COMPLETE_VERIFICATION"

    const val USER_STATUS_ON_BOARDED: String = "ON_BOARDED"
    const val USER_STATUS_MEETING_SCHEDULED: String = "MEETING_SCHEDULED"
    const val USER_STATUS_MEETING_SUCCESS: String = "MEETING_SUCCESS"

    const val name = "name"
    const val data = "payLoad"
    const val result = "result"
    const val skipped = "skipped"
    val FRAGMENT_CLASS = "fragment_class"
    val SHOW_TOOLBAR = "_show_toolbar"
    val EXTRA = "_bundle_extras"
    const val OVERVIEW_BENEFICIARY = "overview_beneficiary"
    const val IS_IBAN_NEEDED = "is_iban_need"

    // SharedPreference  Keys

    const val KEY_APP_UUID = "KEY_APP_UUID"
    const val KEY_PASSCODE: String = "PASSCODE"
    const val KEY_USERNAME: String = "USEERNAME"
    const val KEY_TOUCH_ID_ENABLED: String = "TOUCH_ID_ENABLED"
    const val KEY_IS_USER_LOGGED_IN: String = "KEY_IS_USER_LOGGED_IN"
    const val KEY_IS_FIRST_TIME_USER: String = "KEY_IS_FIRST_TIME_USER"
    const val KEY_IS_FINGERPRINT_PERMISSION_SHOWN: String =
        "KEY_IS_FINGERPRINT_PERMISSION_SHOWN"
    const val KEY_AVAILABLE_BALANCE: String = "AVAILABLE_BALANCE"
    const val KEY_THEME = "KEY_THEME"
    const val VERIFY_PASS_CODE_BTN_TEXT = "verify_pass_code_btn_text"


}