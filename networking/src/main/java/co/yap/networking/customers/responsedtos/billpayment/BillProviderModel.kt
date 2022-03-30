package co.yap.networking.customers.responsedtos.billpayment

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillProviderModel(
    @SerializedName("categoryId")
    var categoryId: String? = null,
    @SerializedName("categoryName")
    var categoryName: String? = null,
    @SerializedName("categoryType")
    var categoryType: String? = null,
    @SerializedName("icon")
    var icon: String? = null
) : Parcelable, ApiResponse()
