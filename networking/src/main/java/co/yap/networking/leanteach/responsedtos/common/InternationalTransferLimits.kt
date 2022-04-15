package co.yap.networking.leanteach.responsedtos.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InternationalTransferLimits(
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("min") var min: Int? = null,
    @SerializedName("max") var max: Int? = null
) : Parcelable