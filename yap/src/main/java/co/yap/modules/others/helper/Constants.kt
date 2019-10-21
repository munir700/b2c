package co.yap.modules.others.helper

object Constants {

    const val CARD_TYPE_DEBIT: String = "DEBIT"
    const val CARD_TYPE_PREPAID: String = "PREPAID"

    const val TEXT_PRIMARY_CARD: String = "Primary card"
    const val TEXT_SPARE_CARD_VIRTUAL: String = "Spare virtual card"
    const val TEXT_SPARE_CARD_PHYSICAL: String = "Spare physical card"

    const val EVENT_ADD_CARD_NAME: Int = 1
    const val EVENT_CHANGE_PIN: Int = 2
    const val EVENT_VIEW_STATEMENTS: Int = 3
    const val EVENT_REPORT_CARD: Int = 4
    const val EVENT_REMOVE_CARD: Int = 5

    const val ADD_FUNDS_PRODUCT_CODE: String = "P001"
    const val REMOVE_FUNDS_PRODUCT_CODE: String = "P004"


    const val REQUEST_CARD_NAME_UPDATED: Int = 55
    const val REQUEST_ADD_REMOVE_FUNDS: Int = 56
    const val REQUEST_REPORT_LOST_OR_STOLEN: Int = 57
    const val REQUEST_SET_LIMITS: Int = 58


    const val EVENT_ADD_PHOTO: Int = 6
    const val EVENT_CHOOSE_PHOTO: Int = 7


}