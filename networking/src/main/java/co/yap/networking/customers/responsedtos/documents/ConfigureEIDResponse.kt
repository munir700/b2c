package co.yap.networking.customers.responsedtos.documents

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigureEIDResponse(
    @SerializedName("ageLimit")
    var ageLimit: Int? = 0,
    @SerializedName("country2DigitIsoCode")
    var country2DigitIsoCode: String? = null
) : Parcelable, ApiResponse()
