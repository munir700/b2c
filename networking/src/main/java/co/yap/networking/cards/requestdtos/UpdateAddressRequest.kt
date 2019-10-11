package co.yap.networking.cards.requestdtos

data class UpdateAddressRequest(
    var address1: String? = null,
    var address2: String? = null,
    var latitude: String? = null,
    var longitude: String? = null
)