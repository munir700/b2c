package co.yap.networking.cards.requestdtos


data class AddPhysicalSpareCardRequest(
    val cardName: String? = null,
    val latitude: Double? = 0.0,
    val longitude: Double = 0.0,
    val address1: String? = null

)