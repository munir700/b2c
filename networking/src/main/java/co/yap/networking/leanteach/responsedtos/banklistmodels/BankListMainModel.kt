package co.yap.networking.leanteach.responsedtos.banklistmodels

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BankListMainModel(
    @SerializedName("identifier") var identifier: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("logo") var logo: String? = null,
    @SerializedName("logo_alt") var logoAlt: String? = null,
    @SerializedName("main_color") var mainColor: String? = null,
    @SerializedName("background_color") var backgroundColor: String? = null,
    @SerializedName("theme") var theme: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("active") var active: Boolean? = null,
    @SerializedName("traits") var traits: ArrayList<String> ,
    @SerializedName("supported_account_types") var supportedAccountTypes: ArrayList<String> ,
    @SerializedName("transfer_limits") var transferLimits: ArrayList<TransferLimits>,
    @SerializedName("international_transfer_limits") var internationalTransferLimits: ArrayList<InternationalTransferLimits>,
    @SerializedName("international_destinations") var internationalDestinations: ArrayList<InternationalDestinations>,
    @SerializedName("availability") var availability: Availability?
): ApiResponse(), Parcelable