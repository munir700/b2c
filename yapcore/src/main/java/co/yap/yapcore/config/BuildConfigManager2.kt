package co.yap.yapcore.config

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class BuildConfigManager2(
    var leanPlumSecretKey: String = "",
    var leanPlumKey: String = "",
    var adjustToken: String = ""
) : Parcelable