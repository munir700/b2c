package co.yap.networking.cards.requestdtos

data class OrderCardRequest(
    val nearestLandMark: String? = null,
    val cardName: String? = null,
    val address1: String? = null,
    val address2: String? = null,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val city: String? = null,
    val country: String? = null,
    val designCode: String? = null

)