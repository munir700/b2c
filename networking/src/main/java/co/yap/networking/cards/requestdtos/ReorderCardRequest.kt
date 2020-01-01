package co.yap.networking.cards.requestdtos

data class ReorderCardRequest(
    val cardSerialNumber: String? = null,
    val address1: String? = null,
    val latitude: String? = null,
    val longitude: String? = null
)