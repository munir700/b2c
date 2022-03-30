package co.yap.networking.leanteach.responsedtos.banklistmodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransferLimits(
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("min") var min: Int? = null,
    @SerializedName("max") var max: Int? = null
) : Parcelable