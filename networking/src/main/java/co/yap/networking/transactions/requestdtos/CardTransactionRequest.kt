package co.yap.networking.transactions.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardTransactionRequest(
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("serialNumber")
    val serialNumber: String? = null
) : Parcelable
