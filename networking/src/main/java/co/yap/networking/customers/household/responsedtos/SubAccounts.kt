package co.yap.networking.customers.household.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubAccounts(
    @SerializedName("data") var account: MutableList<SubAccount>? = mutableListOf()
) : ApiResponse(), Parcelable

@Parcelize
data class SubAccount(
    @SerializedName("accountUuid") var accountUuid: String? = null,
    @SerializedName("cardStatus") var cardStatus: String? = "",
    @SerializedName("customerStatus") var customerStatus: String? = null,
    @SerializedName("customerUuid") var customerUuid: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("firstName") var firstName: String? = "",
    @SerializedName("lastName") var lastName: String? = "",
    @SerializedName("profilePictureUrl") var profilePictureUrl: String? = "",
    @SerializedName("activeAccount") var activeAccount: Boolean? = false,
    @SerializedName("pinCreated") var pinCreated: Boolean? = false,
    @SerializedName("salaryTransferred") var salaryTransferred: Boolean? = false,
    @SerializedName("deliveryStatue") var deliveryStatue: String? = "",
    @SerializedName("salaryDueDate") var salaryDueDate: String? = "",
    @Transient var accountType: String? = "B2C_HOUSEHOLD"
) : ApiResponse(), Parcelable {
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}
