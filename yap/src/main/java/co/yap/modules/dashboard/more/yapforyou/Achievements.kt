package co.yap.modules.dashboard.more.yapforyou

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Achievements(
    val id: Int,
    var title: String,
    var percentage: String?,
    val bgColor: Int,
    var hasCompleted: Boolean?,
    var checkList: ArrayList<String>?
) : Parcelable