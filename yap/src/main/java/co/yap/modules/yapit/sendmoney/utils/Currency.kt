package co.yap.modules.yapit.sendmoney.utils

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*


class Currency : Parcelable {
    var id: Int = 0
    var code: String? = ""
    var name: String? = ""
    var symbol: String? = ""
    var flag = -1
    @SerializedName("cashPickUp")
    var isCashPickUpAllowed: Boolean = false
    var isRmt: Boolean = false

    /**
     * loads currency of the country
     *
     * @param countryCode 2 letter code of country
     */
    constructor(countryCode: String) {
        updateFromCountryCode(countryCode)
    }

    constructor(code: String, name: String, symbol: String, flag: Int) {
        this.code = code
        this.name = name
        this.symbol = symbol
        this.flag = flag
    }

    constructor(code: String, name: String, symbol: String) {
        this.code = code
        this.name = name
        this.symbol = symbol
    }

    fun updateFromCountryCode(countryCode: String) {
        val c = CurrencyUtils.getCurrencyByCountryCode(countryCode)
        if (c != null) {
            code = c!!.code
            name = c!!.name
            flag = c!!.flag
            symbol = c!!.symbol
        }
    }

    constructor() {}

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

    /*
     * PARCEL
     */


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeString(this.code)
        dest.writeString(this.name)
        dest.writeString(this.symbol)
        dest.writeInt(this.flag)
        dest.writeByte(if (this.isCashPickUpAllowed) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.isRmt) 1.toByte() else 0.toByte())
    }

    protected constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.code = `in`.readString()
        this.name = `in`.readString()
        this.symbol = `in`.readString()
        this.flag = `in`.readInt()
        this.isCashPickUpAllowed = `in`.readByte().toInt() != 0
        this.isRmt = `in`.readByte().toInt() != 0
    }


    companion object CREATOR : Parcelable.Creator<Currency> {
        override fun createFromParcel(parcel: Parcel): Currency {
            return Currency(parcel)
        }

        override fun newArray(size: Int): Array<Currency?> {
            return arrayOfNulls(size)
        }
    }
}
