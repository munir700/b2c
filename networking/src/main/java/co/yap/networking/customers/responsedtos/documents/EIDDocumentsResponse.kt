package co.yap.networking.customers.responsedtos.documents

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EIDDocumentsResponse(
    @SerializedName("customerUUID")
    var customerUUID: String? = "",
    @SerializedName("documentType")
    var documentType: String? = "",
    @SerializedName("firstName")
    var firstName: String? = "",
    @SerializedName("middleName")
    var middleName: String? = "",
    @SerializedName("lastName")
    var lastName: String? = "",
    @SerializedName("fullName")
    var fullName: String? = "",
    @SerializedName("nationality")
    var nationality: String? = null,
    @SerializedName("dateExpiry")
    var dateExpiry: String? = null,
    @SerializedName("dateIssue")
    var dateIssue: String? = "",
    @SerializedName("dob")
    var dob: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("identityNo")
    var identityNo: String? = ""
) : ApiResponse(), Parcelable
