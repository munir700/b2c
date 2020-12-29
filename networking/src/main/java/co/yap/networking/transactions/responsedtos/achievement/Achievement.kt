package co.yap.networking.transactions.responsedtos.achievement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Achievement(
    @SerializedName("acheivementType")
    val acheivementType: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("percentage")
    val percentage: Double? = null,
    @SerializedName("isLocked")
    val isForceLocked: Double? = null,
    @SerializedName("tasks")
    val tasks: List<AchievementTask>? = null,


    @SerializedName("colorCode")
    val colorCode: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("percentagee")
    var percentagee: Double? = null,
    @SerializedName("features")
    val features: List<AchievementTask>? = null,
    var icon: Int = -1
) : Parcelable {
    val isCompleted: Boolean get() = percentage == 100.00
}