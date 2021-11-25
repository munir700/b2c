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
    val businessCountries: List<String>? = null,
    @SerializedName("companyName")
    val companyName: String? = null,
    @SerializedName("industrySubSegmentCode")
    val industrySubSegmentCode: String? = null,
    @SerializedName("employmentType")
    val employmentType: String? = null,
    @SerializedName("sponsor")
    val sponsor: String? = null
) : ApiResponse(), Parcelable