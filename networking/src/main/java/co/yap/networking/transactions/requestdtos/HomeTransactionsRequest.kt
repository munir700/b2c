package co.yap.networking.transactions.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeTransactionsRequest(
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("amountStartRange")
    val amountStartRange: Double? = null,
    @SerializedName("amountEndRange")
    val amountEndRange: Double? = null,
    @SerializedName("txnType")
    val txnType: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("totalAppliedFilter")
    val totalAppliedFilter: Int? = null
) : Parcelable
