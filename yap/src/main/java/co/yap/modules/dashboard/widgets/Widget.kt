package co.yap.modules.dashboard.widgets

import co.yap.networking.models.ApiResponse

data class Widget(
    val id: Int? = null,
    val title: String? = null
) : ApiResponse()
