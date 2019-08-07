package co.yap.networking.cards.requestdtos

data class OrderCardRequest(
	val nearestLandMark: String? = null,
	val cardName: String? = null,
	val address1: String? = null,
	val latitude: Int? = null,
	val longitude: Int? = null
)
