package co.yap.networking.transactions.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardTransactionRequest(
    @SerializedName("number")
    val number: Int? = 0,
    @SerializedName("size")
    val size: Int? = 0,
    @SerializedName("serialNumber")
    val serialNumber: String? = null
) : Parcelable
