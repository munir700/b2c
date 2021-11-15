package co.yap.networking.customers.models.dashboardwidget

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WidgetData(
	@SerializedName("id") val id: Int,
	@SerializedName("name") val name: String,
	@SerializedName("icon") var icon: String? = null,
	@SerializedName("status") var status: Boolean? = false,
	@SerializedName("shuffleIndex") var shuffleIndex: Int? = -1,
	@Transient var isPinned: Boolean? = false,
	@Transient var isShuffled: Boolean? = false
):ApiResponse(),Parcelable