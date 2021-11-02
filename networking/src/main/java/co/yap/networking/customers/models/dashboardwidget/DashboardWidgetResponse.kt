package co.yap.networking.customers.models.dashboardwidget

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class DashboardWidgetResponse(

	@SerializedName("errors") val errors: String,
	@SerializedName("data") val widgetList: MutableList<WidgetData>
) : ApiResponse()