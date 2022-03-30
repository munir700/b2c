package co.yap.networking.transactions.responsedtos.billpayments

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnalyticsBill(
        @SerializedName("logo")
        val logo: String? = null,
        @SerializedName("billerName")
        val billerName: String? = null,
        @SerializedName("billDate")
        var billDate: String? = null,
        @SerializedName("billAmount")
        val billAmount: String? = null
) : Parcelable
