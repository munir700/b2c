package co.yap.modules.dashboard.more.yapforyou

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AchievementTask(
    var taskName: String,
    var isCompleted: Boolean
) : Parcelable