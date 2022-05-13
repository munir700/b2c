package co.yap.networking.customers.responsedtos.employment_amendment

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmploymentInfoAmendmentResponse(
    @SerializedName("employmentStatus")
    val employmentStatus: String? = null,
    @SerializedName("employerName")
    val employerName: String? = null,
    @SerializedName("monthlySalary")
    val monthlySalary: String? = null,
    @SerializedName("expectedMonthlyCredit")
    val expectedMonthlyCredit: String? = null,
    @SerializedName("businessCountries")
    val businessCountries: ArrayList<String>? = null,
    @SerializedName("companyName")
    val companyName: String? = null,
    @SerializedName("industrySubSegmentCode")
    val industrySubSegmentCode: List<String>? = null,
    @SerializedName("employmentType")
    val employmentType: String? = null,
    @SerializedName("sponsorName")
    val sponsorName: String? = null,
    @SerializedName("isAmendment")
    val isAmendment: Boolean? = null,
    @SerializedName("typeOfSelfEmployment")
    val typeOfSelfEmployment: String? = null,
    @SerializedName("employmentTypeValue")
    val employmentTypeValue: String? = null
) : ApiResponse(), Parcelable