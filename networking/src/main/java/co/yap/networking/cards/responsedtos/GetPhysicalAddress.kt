package co.yap.networking.cards.responsedtos

import co.yap.networking.models.ApiResponse

data class GetPhysicalAddress(
    val address1: String,
    val address2: String? = null,
    val latitude: Double,
    val longitude: Double
) : ApiResponse()

