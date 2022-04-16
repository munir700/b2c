package co.yap.networking.leanteach.responsedtos.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InternationalDestinations(
    @SerializedName("country_iso_code") var countryIsoCode: String? = null,
    @SerializedName("country_name") var countryName: String? = null
) : Parcelable