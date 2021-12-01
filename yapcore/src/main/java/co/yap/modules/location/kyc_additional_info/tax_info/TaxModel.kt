package co.yap.modules.location.kyc_additional_info.tax_info

import android.os.Parcelable
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxModel(
    var countries: ArrayList<Country> = arrayListOf(),
    var reasons: ArrayList<String> = arrayListOf(),
    var selectedReason: String = reasons.first(),

    var options: ArrayList<String> = arrayListOf("No", "Yes"),
    var selectedOption: ObservableField<String> = ObservableField("No"),

    var tinNumber: ObservableField<String> = ObservableField(""),

    @Transient var previousTinNumber: ObservableField<String>? = ObservableField(),
    @Transient var tagOfTinNumber: ObservableField<String>? = ObservableField(),
    @Transient var previousCountry: ObservableField<String>? = ObservableField(),
    @Transient var tagOfCountry: ObservableField<String>? = ObservableField(),
    @Transient var isRuleValid : Boolean? = true,

    val taxRowTitle: ObservableField<String> = ObservableField("Select country of tax residence"),

    var taxRowNumber: ObservableField<Boolean>,
    var canAddMore: ObservableField<Boolean>,
    var selectedCountry: Country? = null
) : Parcelable