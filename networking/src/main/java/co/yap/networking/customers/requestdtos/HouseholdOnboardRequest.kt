package co.yap.networking.customers.requestdtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class HouseholdOnboardRequest(
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("countryCode")
    var countryCode: String? = null,
    @SerializedName("mobileNo")
    var mobileNo: String? = null,
    @SerializedName("accountType")
    var accountType: String? = null,
    @SerializedName("feeFrequency")
    var feeFrequency: String? = null,
    @Transient var tempPassCode: String? = null
) : ApiResponse(), Parcelable {
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}
