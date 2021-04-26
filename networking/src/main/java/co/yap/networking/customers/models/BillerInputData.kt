package co.yap.networking.customers.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillerInputData(
    @SerializedName("key") val key: String? = null,
    @SerializedName("value") val value: String? = null
) : Parcelable
