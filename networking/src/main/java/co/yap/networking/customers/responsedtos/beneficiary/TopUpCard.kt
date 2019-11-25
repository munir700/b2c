package co.yap.networking.customers.responsedtos.beneficiary

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopUpCard(
    val id: String? = "",
    val logo: String? = "",
    val expiry: String? = "",
    var number: String? = "",
    val alias: String? = "",
    val color: String? = ""
) : ApiResponse(), Parcelable