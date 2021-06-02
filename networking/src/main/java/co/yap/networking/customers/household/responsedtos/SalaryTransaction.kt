package co.yap.networking.customers.household.responsedtos
import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SalaryTransaction(
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("creationDate") var transferDate: String? = null,
    @SerializedName("scheduledPaymentUuid") var scheduledPaymentUuid: String? = null
):ApiResponse(),Parcelable