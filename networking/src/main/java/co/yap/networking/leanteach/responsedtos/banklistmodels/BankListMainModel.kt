package co.yap.networking.leanteach.responsedtos.banklistmodels

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
    @SerializedName("traits") var traits: ArrayList<String>? = null,
    @SerializedName("supported_account_types") var supportedAccountTypes: ArrayList<String>? = null,
    @SerializedName("transfer_limits") var transferLimits: ArrayList<TransferLimits>? = null,
    @SerializedName("international_transfer_limits") var internationalTransferLimits: ArrayList<InternationalTransferLimits>? = null,
    @SerializedName("international_destinations") var internationalDestinations: ArrayList<InternationalDestinations>? = null,
    @SerializedName("availability") var availability: Availability? = null,
    @Transient var status: String? = null
) : ApiResponse(), Parcelable

@Parcelize
data class TransferLimits(
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("min") var min: Int? = null,
    @SerializedName("max") var max: Int? = null
) : Parcelable

@Parcelize
data class InternationalDestinations(
    @SerializedName("country_iso_code") var countryIsoCode: String? = null,
    @SerializedName("country_name") var countryName: String? = null
) : Parcelable

@Parcelize
data class InternationalTransferLimits(
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("min") var min: Int? = null,
    @SerializedName("max") var max: Int? = null
) : Parcelable

@Parcelize
data class Availability(
    @SerializedName("active") var active: Active?,
    @SerializedName("enabled") var enabled: Enabled?
) : Parcelable

@Parcelize
data class Active(
    @SerializedName("payments") var payments: Boolean? = null,
    @SerializedName("data") var data: Boolean? = null
) : Parcelable

@Parcelize
data class Enabled(
    @SerializedName("payments") var payments: Boolean? = null,
    @SerializedName("data") var data: Boolean? = null
) : Parcelable