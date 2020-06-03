package co.yap.modules.location

import android.content.Context
import co.yap.countryutils.country.utils.CurrencyUtils

data class POBCountry(
    val name: String?,
    val isoCountryCode2Digit: String? = null,
    val isoCountryCode3Digit: String? = null
) {
    fun getFlagDrawableResId(context: Context): Int {
        return if (!isoCountryCode2Digit.isNullOrEmpty())
            CurrencyUtils.getFlagDrawable(context, isoCountryCode2Digit)
        else
            0
    }
}