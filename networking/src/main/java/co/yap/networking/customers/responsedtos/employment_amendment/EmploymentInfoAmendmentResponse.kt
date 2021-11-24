package co.yap.networking.customers.responsedtos.employment_amendment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class EmploymentInfoAmendmentResponse(
    @SerializedName("errors")
    val errors: String? = null,
    @SerializedName("data")
    val data: EmploymentStatus? = null
) : ApiResponse()

data class EmploymentStatus(
    @SerializedName("employmentStatus")
    val status: String? = null,
    @SerializedName("employerName")
    val employerName: String? = null,
    @SerializedName("monthlySalary")
    val monthlySalary: String? = null,
    @SerializedName("expectedMonthlyCredit")
    val expectedMonthlyCredit: String? = null
)