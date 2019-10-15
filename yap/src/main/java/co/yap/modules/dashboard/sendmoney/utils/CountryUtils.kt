package co.yap.modules.dashboard.sendmoney.utils

import android.content.Context
import android.text.TextUtils
import co.yap.yapcore.helpers.Utils
import java.util.*
import java.util.Currency

object CountryUtils {

//val contextCountryUtils :Context= Utils.context!!

    fun getCountriesList(context: Context): List<Country> {
        val codes = Locale.getISOCountries()
        val countries = ArrayList<Country>()
        for (countryCode in codes) {
            val loc = Locale("", countryCode)
            val countryName = loc.displayCountry
            val c = Country()
            c.setName(countryName)
            c.code = countryCode.toString()
            if (countryName.length > 0 && !countries.contains(c)) {
                countries.add(c)
                val flagResName = "flag_" + countryCode.toLowerCase()
                val flag = getFlagDrawable(Utils.context!!, flagResName)
                c.setFlagDrawableResId(flag)
            }
        }
        return countries
    }


    fun getFlagDrawable(contextCountryUtils: Context, resName: String): Int {
        if (!TextUtils.isEmpty(resName)) {
            var name = resName
            if (resName.length == 2 || resName.length == 3) {
                // it is probably a country iso code
                name = "flag_" + resName.toLowerCase()
            }
            return Utils.context!!.resources.getIdentifier(
                name,
                "drawable",
                Utils!!.context!!.packageName
            )
        }
        return 0
    }

    fun getFlagDrawable(countryIsoName: String): Int {
        return getFlagDrawable(Utils.context!!, countryIsoName)
    }

    fun getCountryName(countryCode: String): String {
        if (TextUtils.isEmpty(countryCode)) return ""
        val loc = Locale("", countryCode)
        return loc.displayCountry
    }

    fun getCurrencySymbol(currencyCode: String): String {
        val currency = Currency.getInstance(currencyCode)
        return currency.symbol
    }
}