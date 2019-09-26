package co.yap.networking.cards.requestdtos


data class AddPhysicalSpareCardRequest(
    val cardName: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val address1: String? = null

)