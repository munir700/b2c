package co.yap.modules.dashboard.widgets

import co.yap.networking.models.ApiResponse

data class WidgetList(
    var title: String? = null,
    var widget: List<Widget>
) : ApiResponse()