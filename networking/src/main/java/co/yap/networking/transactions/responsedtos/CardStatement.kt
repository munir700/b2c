package co.yap.networking.transactions.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardStatement(
    @SerializedName("statementURL")
    val statementURL: String? = "",
    @SerializedName("month")
    val month: String? = "",
    @SerializedName("year")
    val year: String? = "",
    @Transient
    var sendEmail: Boolean? = false,
    @Transient
    var statementType: String? = "",
    @Transient
    var cardType: String? = ""
) : ApiResponse(), Parcelable

