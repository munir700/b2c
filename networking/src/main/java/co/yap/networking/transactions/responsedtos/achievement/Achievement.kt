package co.yap.networking.transactions.responsedtos.achievement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Achievement(
    @SerializedName("achievementType")
    val achievementType: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("colorCode")
    val color: String? = null,
    @SerializedName("percentage")
    val percentage: Double? = null,
    @SerializedName("lock")
    val isForceLocked: Boolean? = null,
    @SerializedName("tasks")
    val tasks: List<AchievementTask>? = null
) : Parcelable {
    val isCompleted: Boolean get() = percentage == 100.00
}