package co.yap.networking.transactions.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeTransactionsRequest(
    @SerializedName("number")
    val number: Int? = 0,
    @SerializedName("size")
    val size: Int? = 0,
    @SerializedName("amountStartRange")
    val amountStartRange: Double? = 0.0,
    @SerializedName("amountEndRange")
    val amountEndRange: Double? = 0.0,
    @SerializedName("txnType")
    val txnType: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("totalAppliedFilter")
    val totalAppliedFilter: Int? = 0
) : Parcelable
