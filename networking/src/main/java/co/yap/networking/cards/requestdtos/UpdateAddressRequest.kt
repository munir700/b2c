package co.yap.networking.cards.requestdtos

data class UpdateAddressRequest(
    var address1: String,
    var address2: String,
    var latitude: String,
    var longitude: String
)