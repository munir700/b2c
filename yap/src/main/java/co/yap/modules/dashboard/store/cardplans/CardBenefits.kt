package co.yap.modules.dashboard.store.cardplans

import co.yap.networking.models.ApiResponse

data class CardBenefits(
    val benefit: String?,
    var isLast : Boolean = false
):ApiResponse()
