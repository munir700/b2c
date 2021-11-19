package co.yap.billpayments.utils.enums

import co.yap.yapcore.R

enum class BillCategory(val title: String, val color: Int) {
    CREDIT_CARD("Credit Card", R.color.colorPrimarySoft),
    TELECOM("telecom", R.color.colorSecondaryOrange),
    UTILITIES("utility", R.color.greyDark),
    TRANSPORT("transport", R.color.colorSecondaryBlue)
}
