package co.yap.networking.cards.requestdtos

data class CardsHotlistRequest(
    val cardSerialNumber: String,
    val hotListReason: String
)