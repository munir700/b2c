package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse

data class HouseHoldCardsDesignResponse(
    val data: List<HouseHoldCardsDesign>? = null
) : ApiResponse()
