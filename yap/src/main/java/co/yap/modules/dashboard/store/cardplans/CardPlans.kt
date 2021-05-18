package co.yap.modules.dashboard.store.cardplans

import co.yap.networking.models.ApiResponse

data class CardPlans(
    val id: String,
    val title: String?,
    val type: String?,
    val description: String?,
    val resource: Int?,
    val cardIcon: Int?
):ApiResponse()
