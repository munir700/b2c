package co.yap.networking.customers.models

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityModel(
    @SerializedName("data")
    var data: ArrayList<String>? = null,
    @SerializedName("errors")
    var errors: String? = null
) : ApiResponse(), Parcelable