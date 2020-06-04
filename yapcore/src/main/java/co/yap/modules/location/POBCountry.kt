package co.yap.modules.location

import android.content.Context
import android.os.Parcelable
import co.yap.countryutils.country.utils.CurrencyUtils
import kotlinx.android.parcel.Parcelize

@Parcelize
data class POBCountry(
    val name: String?,
    val isoCountryCode2Digit: String? = null,
    val isoCountryCode3Digit: String? = null
) : Parcelable {
    fun getFlagDrawableResId(context: Context): Int {
        return if (!isoCountryCode2Digit.isNullOrEmpty())
            CurrencyUtils.getFlagDrawable(context, isoCountryCode2Digit)
        else
            0
    }
}