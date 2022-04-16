package co.yap.networking.leanteach.responsedtos.accountlistmodel

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeanCustomerAccounts(
    @SerializedName("paymentSourceId") var paymentSourceId: String? = null,
    @SerializedName("iban") var iban: String? = null,
    @SerializedName("accountNumber") var accountNumber: String? = null,
    @SerializedName("accountName") var accountName: String? = null,
    @SerializedName("accountId") var accountId: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("identifier") var identifier: String? = null
) : ApiResponse(), Parcelable