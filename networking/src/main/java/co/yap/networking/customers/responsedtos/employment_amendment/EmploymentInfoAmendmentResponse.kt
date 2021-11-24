package co.yap.networking.customers.responsedtos.employment_amendment

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmploymentInfoAmendmentResponse(
    @SerializedName("employmentStatus")
    val status: String? = null,
    @SerializedName("employerName")
    val employerName: String? = null,
    @SerializedName("monthlySalary")
    val monthlySalary: String? = null,
    @SerializedName("expectedMonthlyCredit")
    val expectedMonthlyCredit: String? = null
) : ApiResponse(), Parcelable