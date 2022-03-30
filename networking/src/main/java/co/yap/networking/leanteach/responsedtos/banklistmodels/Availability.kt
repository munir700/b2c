package co.yap.networking.leanteach.responsedtos.banklistmodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Availability(
    @SerializedName("active") var active: Active? ,
    @SerializedName("enabled") var enabled: Enabled?
): Parcelable