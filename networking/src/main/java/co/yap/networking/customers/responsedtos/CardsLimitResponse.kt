package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse

data class CardsLimitResponse(
    var data: Data,
    var errors: Any?
): ApiResponse()