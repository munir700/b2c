package co.yap.networking.cards.requestdtos

data class OrderCardRequest(
	val nearestLandMark: String? = null,
	val cardName: String? = null,
	val address1: String? = null,
	val latitude: Double? = 0.0,
	val longitude: Double = 0.0
)
