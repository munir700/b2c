package co.yap.networking.customers.responsedtos.household

import android.os.Parcelable
import co.yap.networking.customers.responsedtos.Customer
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HouseHoldUserProfile(
    @SerializedName("data") var hhUser: Customer?
) : ApiResponse(), Parcelable
