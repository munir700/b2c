package co.yap.networking.transactions.responsedtos

import com.google.gson.annotations.SerializedName

class FundTransferLimits(
    @SerializedName("minLimit") val minLimit: String,
    @SerializedName("maxLimit") val maxLimit: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("dailyAccumulativeMinLimit") val dailyAccumulativeMinLimit: String?,
    @SerializedName("dailyAccumulativeMaxLimit") val dailyAccumulativeMaxLimit: String?,
    @SerializedName("remainingAvailableLimit") val remainingAvailableLimit: String?
)