package co.yap.countryutils.country.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Currency(
    var code: String? = "",
    var name: String? = "",
    var symbol: String? = "",
    var id: Int = 0,
    var default: Boolean? = false,
    var flag: Int? = -1,
    var isCashPickUpAllowed: Boolean = false,
    var isRmt: Boolean = false,
    var active: Boolean? = false
) : Parcelable {

    /**
     * loads currency of the country
     *
     * @param countryCode 2 letter isoCountryCode2Digit of country
     */

    init {
        code?.let {
            updateFromCountryCode(it)
        }
    }

    fun updateFromCountryCode(countryCode: String) {
        val c =
            CurrencyUtils.getCurrencyByCountryCode(
                countryCode
            )
        if (c != null) {
            code = c!!.code
            name = c!!.name
            flag = c!!.flag
            symbol = c!!.symbol
        }
    }

    override fun equals(obj: Any?): Boolean {
        val obj1 = obj as Currency?
        return if (obj1!!.code == code
            && obj1.id == id
            && obj1.name == name
        ) true else super.equals(obj)
    }

    /*
     * COMPARATORS
     */

    class ISOCodeComparator : Comparator<Currency> {
        override fun compare(currency: Currency, t1: Currency): Int {
            return currency.code!!.compareTo(t1.code!!)
        }
    }


    class NameComparator : Comparator<Currency> {
        override fun compare(currency: Currency, t1: Currency): Int {
            return currency.name!!.compareTo(t1.name!!)
        }
    }
}
