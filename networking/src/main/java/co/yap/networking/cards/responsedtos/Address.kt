package co.yap.networking.cards.responsedtos

data class Address(
    val address1: String,
    val address2: String? = null,
    val latitude: Double,
    val longitude: Double
)