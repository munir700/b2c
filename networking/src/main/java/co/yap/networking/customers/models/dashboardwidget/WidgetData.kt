package co.yap.networking.customers.models.dashboardwidget

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class WidgetData(
	@SerializedName("id") val id: Int,
	@SerializedName("name") val name: String,
	@SerializedName("icon") var icon: String? = null,
	@SerializedName("status") val status: Boolean? = false,
	@SerializedName("shuffleIndex") val shuffleIndex: Int? = -1
): ApiResponse()