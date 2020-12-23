package co.yap.networking.customers.requestdtos

import com.google.gson.annotations.SerializedName

data class TourGuideRequest(
    @SerializedName("viewName")
    val viewName: String,
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("skipped")
    val skipped: Boolean,
    @SerializedName("viewed")
    val viewed: Boolean
)