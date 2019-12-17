package co.yap.networking.customers.requestdtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OtherBankQuery(
    @SerializedName("other_bank_country")
    var other_bank_country: String? = "",
    @SerializedName("max_records")
    var max_records: Int? = 0,
    @SerializedName("params")
    val params: ArrayList<Params>? = null
) : ApiResponse(), Parcelable {
    @Parcelize
    data class Params(
        val id: String? = "",
        val value: String? = ""
    ) : ApiResponse(), Parcelable
}