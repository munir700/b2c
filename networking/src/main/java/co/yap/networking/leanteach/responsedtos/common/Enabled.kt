package co.yap.networking.leanteach.responsedtos.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Enabled(
    @SerializedName("payments") var payments: Boolean? = null,
    @SerializedName("data") var data: Boolean? = null
) : Parcelable