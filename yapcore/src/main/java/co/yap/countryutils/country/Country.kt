package co.yap.countryutils.country

import android.os.Parcel
import android.os.Parcelable
import co.yap.countryutils.country.utils.Currency
import co.yap.countryutils.country.utils.CurrencyUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Country : Parcelable, Serializable {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("isoCountryCode3Digit")
    var isoCountryCode3Digit: String? = null

      @SerializedName("cashPickUp")
    private var cashPickUpAllowed: Boolean = false

      @SerializedName("rmtCountry")
    private var rmt: Boolean = false

    @SerializedName("isoCountryCode2Digit")
    var isoCountryCode2Digit: String? = null

    private var name: String? = null
    @SerializedName("currencyList")
    var supportedCurrencies: List<Currency>? = null

    private var flagDrawableResId = -1
     private var currency: Currency? = null

    val isFlagAvailable: Boolean
        get() = getFlagDrawableResId() > 0

    var isCashPickUpAllowed: Boolean
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

    var isRmt: Boolean
        get() {
            val size = supportedCurrencies!!.size
            if (size > 0) {
                val mainCurrency = supportedCurrencies!![size - 1]
                return mainCurrency.isRmt
            }
            return rmt
        }
        @Deprecated("")
        set(rmt) {
            this.rmt = rmt
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
                this!!.isoCountryCode2Digit!!
            )
        return flagDrawableResId
    }

    fun setFlagDrawableResId(flagDrawableResId: Int) {
        this.flagDrawableResId = flagDrawableResId
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id!!)
        dest.writeString(this.isoCountryCode3Digit)
        dest.writeByte(if (this.cashPickUpAllowed) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.rmt) 1.toByte() else 0.toByte())
        dest.writeString(this.isoCountryCode2Digit)
        dest.writeString(this.name)
        dest.writeTypedList(this.supportedCurrencies)
        dest.writeInt(this.flagDrawableResId)
        dest.writeParcelable(this.currency, flags)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.isoCountryCode3Digit = `in`.readString()
        this.cashPickUpAllowed = `in`.readByte().toInt() != 0
        this.rmt = `in`.readByte().toInt() != 0
        this.isoCountryCode2Digit = `in`.readString()
        this.name = `in`.readString()
        this.supportedCurrencies = `in`.createTypedArrayList<Currency>(
            Currency
        )
        this.flagDrawableResId = `in`.readInt()
        this.currency = `in`.readParcelable<Parcelable>(Currency::class.java!!.getClassLoader()) as Currency?
    }

    companion object CREATOR :
        Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}