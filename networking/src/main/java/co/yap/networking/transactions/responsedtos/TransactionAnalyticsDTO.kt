package co.yap.networking.transactions.responsedtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionAnalyticsDTO(
    @SerializedName("totalTxnCount")
    val totalTxnCount: Int? = null,
    @SerializedName("totalTxnAmount")
    val totalTxnAmount: Double? = null,
    @SerializedName("monthlyAvgAmount")
    val monthlyAvgAmount: Double? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("txnAnalytics")
    val txnAnalytics: ArrayList<TxnAnalytic>? = ArrayList()
) : Parcelable