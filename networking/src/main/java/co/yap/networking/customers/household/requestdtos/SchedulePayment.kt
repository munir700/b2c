package co.yap.networking.customers.household.requestdtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchedulePayment(
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("categoryName") var categoryName: String? = "salary",
    @SerializedName("currencyCode") var currencyCode: String? = "AED",
    @SerializedName("recurring") var isRecurring: Boolean? = false,
    @SerializedName("lastProcessingDate") var lastProcessingDate: String? = "", // not used any where
    @SerializedName("nextProcessingDate") var nextProcessingDate: String? = "",// only nextProcessingDate used used
    @SerializedName("paymentDate") var paymentDate: String? = null,
    @SerializedName("productCode") var productCode: String? = "P019",
    @SerializedName("recurringInterval") var recurringInterval: String? = "",
    @SerializedName("scheduledDate") var scheduledDate: String? = null, //not used any where
    @SerializedName("subCategory") var subCategory: String? = "salary",
    @SerializedName("scheduledPaymentUuid") var scheduledPaymentUuid: String? =  null,
    @SerializedName("accountUuid1") var accountUuid1: String? = null,
    @SerializedName("accountUuid2") var accountUuid2: String? = null,
    @SerializedName("status") var status: String? = null
) :ApiResponse(), Parcelable
