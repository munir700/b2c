package co.yap.networking.transactions.responsedtos.billpayments

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BPAnalyticsModel(
        @SerializedName("categoryId")
        val categoryId: String? = null,
        @SerializedName("categoryName")
        val categoryName: String? = null,
        @SerializedName("totalSpending")
        val totalSpending: String? = null,
        @SerializedName("icon")
        val icon: String? = null,
        @SerializedName("txnCount")
        val txnCount: Int? = null,
        @SerializedName("totalSpendingInPercentage")
        val totalSpendingInPercentage: Double? = null
) : Parcelable
