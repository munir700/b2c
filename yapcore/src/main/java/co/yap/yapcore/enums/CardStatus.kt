package co.yap.yapcore.enums

import androidx.annotation.Keep

@Keep
enum class CardStatus {
    ACTIVE, BLOCKED, INACTIVE, HOTLISTED, EXPIRED,CANCELLED,CLOSED;

//    ACTIVE("Active"),
//    INACTIVE("Inactive"),
//    BLOCKED("Blocked"),
//    HOTLISTED("Hotlisted"),
//    CLOSED("Closed"),
//    CARD_REORDERED("Card Reordered"),
//    CANCELLED("Cancelled"),
//    EXPIRED("Expired"),
//    PIN_BLOCKED("Pin Blocked"),
//    PIN_UN_BLOCKED("Pin Unblocked")

    //Zain ul Abe Din Sohail Zahid There is new status added in card status PIN_BLOCKED.
    // Please handle this at your side also because currently at backend we have added
    // this status and now cards are not appearing on mobile app.
}