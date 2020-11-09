package co.yap.networking.customers.responsedtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class QRContact (
    @SerializedName("profilePictureName")
    private var profilePictureName: String?,
    @SerializedName("countryCode")
    var countryCode: String,
    @SerializedName("mobileNo")
    var mobileNo: String,
    @SerializedName("firstName")
    var firstName: String? = "",
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("usNationalForTax")
    var usNationalForTax: Boolean? = false
    ) : Parcelable