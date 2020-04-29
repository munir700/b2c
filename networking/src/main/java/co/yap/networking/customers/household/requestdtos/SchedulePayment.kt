package co.yap.networking.customers.household.requestdtos

import com.google.gson.annotations.SerializedName


data class SchedulePayment(
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("categoryName") var categoryName: String? = null,
    @SerializedName("currencyCode") var currencyCode: String? = null,
    @SerializedName("isRecurring") var isRecurring: Boolean? = null,
    @SerializedName("lastProcessingDate") var lastProcessingDate: String? = null,
    @SerializedName("nextProcessingDate") var nextProcessingDate: String? = null,
    @SerializedName("paymentDate") var paymentDate: String? = null,
    @SerializedName("productCode") var productCode: String? = null,
    @SerializedName("recurringInterval") var recurringInterval: String? = null,
    @SerializedName("scheduledDate") var scheduledDate: String? = null,
    @SerializedName("subCategory") var subCategory: String? = null
)