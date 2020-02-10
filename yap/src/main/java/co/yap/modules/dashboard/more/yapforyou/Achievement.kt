package co.yap.modules.dashboard.more.yapforyou

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Achievement(
    @SerializedName("colorCode")
    val colorCode: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("percentage")
    var percentage: Double?,
    @SerializedName("feature")
    val feature: List<AchievementTask>
) : Parcelable