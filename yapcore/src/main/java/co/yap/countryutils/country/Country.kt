package co.yap.countryutils.country

import android.os.Parcelable
import co.yap.countryutils.country.utils.Currency
import co.yap.countryutils.country.utils.CurrencyUtils
import kotlinx.android.parcel.Parcelize


@Parcelize
class Country(
    var id: Int? = null,
    var isoCountryCode3Digit: String? = null,
    private var cashPickUpAllowed: Boolean? = false,
    private var rmtCountry: Boolean? = false,
    var isoCountryCode2Digit: String? = null,
    var supportedCurrencies: List<Currency>? = null,
    var active: Boolean? = false,
    var isoNum: String? = "",
    var signUpAllowed: Boolean? = false,
    var cashPickUp: Boolean? = false,
    private var name: String? = null,
    private var flagDrawableResId: Int = -1,
    private var currency: Currency? = null
) : Parcelable {


    val isFlagAvailable: Boolean
        get() = getFlagDrawableResId() > 0

    var isCashPickUpAllowed: Boolean?
        get() {
            val size = supportedCurrencies!!.size
            if (size > 0) {
                val mainCurrency = supportedCurrencies!![size - 1]
                return mainCurrency.isCashPickUpAllowed
            }
            return this.cashPickUpAllowed
        }
        @Deprecated("")
        set(cashPickUpAllowed) {
            this.cashPickUpAllowed = cashPickUpAllowed
        }

    var isRmt: Boolean?
        get() {
            val size = supportedCurrencies!!.size
            if (size > 0) {
                val mainCurrency = supportedCurrencies!![size - 1]
                return mainCurrency.isRmt
            }
            return rmtCountry
        }
        @Deprecated("")
        set(rmt) {
            this.rmtCountry = rmt
        }

    fun getName(): String {
        if (name == null) name = ""
        return name as String
    }

    fun setName(name: String) {
        this.name = name
    }


    fun getCurrency(): Currency? {
        if (currency == null) {
            // First Check if we can get the main currency from list of supported currencies.
            val localCurrency =
                CurrencyUtils.getCurrencyByCountryCode(
                    isoCountryCode2Digit!!
                )
            for (c in supportedCurrencies!!) {
                if (c.equals(localCurrency)) {
                    currency = localCurrency
                    break
                }
            }
            if (currency == null) {
                val c = supportedCurrencies!![0]
                // find currency from utils with flag and symbol etc
                currency =
                    CurrencyUtils.getCurrencyByCode(
                        c.code!!
                    )
            }
        }
        return currency
    }

    fun setCurrency(currency: Currency) {
        this.currency = currency
    }

    override fun equals(obj: Any?): Boolean {
        val equals = (obj as Country).getName().equals(getName(), ignoreCase = true)
        return equals || super.equals(obj)
    }

    fun getFlagDrawableResId(): Int {
        if (flagDrawableResId <= 0) flagDrawableResId =
            CurrencyUtils.getFlagDrawable(
                this.isoCountryCode2Digit!!
            )
        return flagDrawableResId
    }

    fun setFlagDrawableResId(flagDrawableResId: Int) {
        this.flagDrawableResId = flagDrawableResId
    }

}
