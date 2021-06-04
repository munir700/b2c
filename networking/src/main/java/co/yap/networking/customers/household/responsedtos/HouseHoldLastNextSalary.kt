package co.yap.networking.customers.household.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HouseHoldLastNextSalary(
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("updatedDate")
    var transferDate: String? = null,
    @SerializedName("currency")
    var currency: String? = null,
    @SerializedName("amount")
    var amount: String? = null,
    @SerializedName("subCategory")
    var subCategory: String? = null
) : ApiResponse(), Parcelable {
}