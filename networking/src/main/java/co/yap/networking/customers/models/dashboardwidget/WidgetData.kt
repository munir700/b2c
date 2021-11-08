package co.yap.networking.customers.models.dashboardwidget

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WidgetData(
	@SerializedName("id") val id: Int,
	@SerializedName("name") val name: String?=null,
	@SerializedName("icon") var icon: String? = null,
	@SerializedName("status") var status: Boolean? = false,
	@SerializedName("shuffleIndex") val shuffleIndex: Int? = -1,
	@Transient var isPinned: Boolean? = false,
	var clickId: Long? = System.currentTimeMillis()
): Parcelable