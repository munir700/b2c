package co.yap.networking.customers.responsedtos.billpayment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IoCatalogModel(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("dataType")
    var dataType: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("validLengths")
    var validLengths: String? = null,
    @SerializedName("minLength")
    var minLength: Int? = null,
    @SerializedName("maxLength")
    var maxLength: Int? = null,
    @SerializedName("ioId")
    var ioId: Int? = null,
    @SerializedName("operation")
    var operation: Int? = null
) : Parcelable
