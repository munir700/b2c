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
    val countryCode: String? = null,
    @SerializedName("mobileNo")
    val mobileNo: String? = null,
    @SerializedName("accountType")
    val accountType: String? = null,
    @SerializedName("feeFrequency")
    val feeFrequency: String?=null
) : ApiResponse(), Parcelable
