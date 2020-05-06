package co.yap.networking.customers.household.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchedulePayment(
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("categoryName") var categoryName: String? = "salary",
    @SerializedName("currencyCode") var currencyCode: String? = "AED",
    @SerializedName("isRecurring") var isRecurring: Boolean? = false,
    @SerializedName("lastProcessingDate") var lastProcessingDate: String? = "",
    @SerializedName("nextProcessingDate") var nextProcessingDate: String? = "",
    @SerializedName("paymentDate") var paymentDate: String? = null,
    @SerializedName("productCode") var productCode: String? = "P019",
    @SerializedName("recurringInterval") var recurringInterval: String? = "",
    @SerializedName("scheduledDate") var scheduledDate: String? = null,
    @SerializedName("subCategory") var subCategory: String? = "salary",
    @SerializedName("scheduledPaymentUuid") var scheduledPaymentUuid: String? = ""
):Parcelable