package co.yap.networking.customers.responsedtos.employment_amendment

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DocumentResponse(
    @SerializedName("onChange")
    val onChange: String? = null,
    @SerializedName("empType")
    val empType: String? = null,
    @SerializedName("employmentProofDocuments")
    val documents: List<Document> = listOf()
) : ApiResponse(), Parcelable