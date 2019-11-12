package co.yap.networking.customers.responsedtos.country.utils

import android.content.Context
import android.text.TextUtils
import co.yap.R
import co.yap.yapcore.helpers.Utils
import java.util.*


object CurrencyUtils {
    val CURRENCIES = arrayOf(
        Currency(
            "EUR",
            "Euro",
            "€",
            R.drawable.flag_eur
        ),
        Currency(
            "USD",
            "United States Dollar",
            "$",
            R.drawable.flag_usd
        ),
        Currency(
            "GBP",
            "British Pound",
            "£",
            R.drawable.flag_gbp
        ),
        Currency(
            "CZK",
            "Czech Koruna",
            "Kč",
            R.drawable.flag_czk
        ),
        Currency(
            "TRY",
            "Turkish Lira",
            "₺",
            R.drawable.flag_try
        ),
        Currency(
            "AED",
            "Emirati Dirham",
            "د.إ",
            R.drawable.flag_aed
        ),
        Currency(
            "AFN",
            "Afghanistan Afghani",
            "؋",
            R.drawable.flag_afn
        ),
        Currency(
            "ARS",
            "Argentine Peso",
            "$",
            R.drawable.flag_ars
        ),
        Currency(
            "AUD",
            "Australian Dollar",
            "$",
            R.drawable.flag_aud
        ),
        Currency(
            "BBD",
            "Barbados Dollar",
            "$",
            R.drawable.flag_bbd
        ),
        Currency(
            "BDT",
            "Bangladeshi Taka",
            " Tk",
            R.drawable.flag_bdt
        ),
        Currency(
            "BGN",
            "Bulgarian Lev",
            "лв",
            R.drawable.flag_bgn
        ),
        Currency(
            "BHD",
            "Bahraini Dinar",
            "BD",
            R.drawable.flag_bhd
        ),
        Currency(
            "BMD",
            "Bermuda Dollar",
            "$",
            R.drawable.flag_bmd
        ),
        Currency(
            "BND",
            "Brunei Darussalam Dollar",
            "$",
            R.drawable.flag_bnd
        ),
        Currency(
            "BOB",
            "Bolivia Bolíviano",
            "\$b",
            R.drawable.flag_bob
        ),
        Currency(
            "BRL",
            "Brazil Real",
            "R$",
            R.drawable.flag_brl
        ),
        Currency(
            "BTN",
            "Bhutanese Ngultrum",
            "Nu.",
            R.drawable.flag_btn
        ),
        Currency(
            "BZD",
            "Belize Dollar",
            "BZ$",
            R.drawable.flag_bzd
        ),
        Currency(
            "CAD",
            "Canada Dollar",
            "$",
            R.drawable.flag_cad
        ),
        Currency(
            "CHF",
            "Switzerland Franc",
            "CHF",
            R.drawable.flag_chf
        ),
        Currency(
            "CLP",
            "Chile Peso",
            "$",
            R.drawable.flag_clp
        ),
        Currency(
            "CNY",
            "China Yuan Renminbi",
            "¥",
            R.drawable.flag_cny
        ),
        Currency(
            "COP",
            "Colombia Peso",
            "$",
            R.drawable.flag_cop
        ),
        Currency(
            "CRC",
            "Costa Rica Colon",
            "₡",
            R.drawable.flag_crc
        ),
        Currency(
            "DKK",
            "Denmark Krone",
            "kr",
            R.drawable.flag_dkk
        ),
        Currency(
            "DOP",
            "Dominican Republic Peso",
            "RD$",
            R.drawable.flag_dop
        ),
        Currency(
            "EGP",
            "Egypt Pound",
            "£",
            R.drawable.flag_egp
        ),
        Currency(
            "ETB",
            "Ethiopian Birr",
            "Br",
            R.drawable.flag_etb
        ),
        Currency(
            "GEL",
            "Georgian Lari",
            "₾",
            R.drawable.flag_gel
        ),
        Currency(
            "GHS",
            "Ghana Cedi",
            "¢",
            R.drawable.flag_ghs
        ),
        Currency(
            "GMD",
            "Gambian dalasi",
            "D",
            R.drawable.flag_gmd
        ),
        Currency(
            "GYD",
            "Guyana Dollar",
            "$",
            R.drawable.flag_gyd
        ),
        Currency(
            "HKD",
            "Hong Kong Dollar",
            "$",
            R.drawable.flag_hkd
        ),
        Currency(
            "HRK",
            "Croatia Kuna",
            "kn",
            R.drawable.flag_hrk
        ),
        Currency(
            "HUF",
            "Hungary Forint",
            "Ft",
            R.drawable.flag_huf
        ),
        Currency(
            "IDR",
            "Indonesia Rupiah",
            "Rp",
            R.drawable.flag_idr
        ),
        Currency(
            "ILS",
            "Israel Shekel",
            "₪",
            R.drawable.flag_ils
        ),
        Currency(
            "INR",
            "Indian Rupee",
            "₹",
            R.drawable.flag_inr
        ),
        Currency(
            "ISK",
            "Iceland Krona",
            "kr",
            R.drawable.flag_isk
        ),
        Currency(
            "JMD",
            "Jamaica Dollar",
            "J$",
            R.drawable.flag_jmd
        ),
        Currency(
            "JPY",
            "Japanese Yen",
            "¥",
            R.drawable.flag_jpy
        ),
        Currency(
            "KES",
            "Kenyan Shilling",
            "KSh",
            R.drawable.flag_kes
        ),
        Currency(
            "KRW",
            "Korea (South) Won",
            "₩",
            R.drawable.flag_krw
        ),
        Currency(
            "KWD",
            "Kuwaiti Dinar",
            "د.ك",
            R.drawable.flag_kwd
        ),
        Currency(
            "KYD",
            "Cayman Islands Dollar",
            "$",
            R.drawable.flag_kyd
        ),
        Currency(
            "KZT",
            "Kazakhstan Tenge",
            "лв",
            R.drawable.flag_kzt
        ),
        Currency(
            "LAK",
            "Laos Kip",
            "₭",
            R.drawable.flag_lak
        ),
        Currency(
            "LKR",
            "Sri Lanka Rupee",
            "₨",
            R.drawable.flag_lkr
        ),
        Currency(
            "LRD",
            "Liberia Dollar",
            "$",
            R.drawable.flag_lrd
        ),
        Currency(
            "LTL",
            "Lithuanian Litas",
            "Lt",
            R.drawable.flag_ltl
        ),
        Currency(
            "MAD",
            "Moroccan Dirham",
            "MAD",
            R.drawable.flag_mad
        ),
        Currency(
            "MDL",
            "Moldovan Leu",
            "MDL",
            R.drawable.flag_mdl
        ),
        Currency(
            "MKD",
            "Macedonia Denar",
            "ден",
            R.drawable.flag_mkd
        ),
        Currency(
            "MNT",
            "Mongolia Tughrik",
            "₮",
            R.drawable.flag_mnt
        ),
        Currency(
            "MUR",
            "Mauritius Rupee",
            "₨",
            R.drawable.flag_mur
        ),
        Currency(
            "MWK",
            "Malawian Kwacha",
            "MK",
            R.drawable.flag_mwk
        ),
        Currency(
            "MXN",
            "Mexico Peso",
            "$",
            R.drawable.flag_mxn
        ),
        Currency(
            "MYR",
            "Malaysia Ringgit",
            "RM",
            R.drawable.flag_myr
        ),
        Currency(
            "MZN",
            "Mozambique Metical",
            "MT",
            R.drawable.flag_mzn
        ),
        Currency(
            "NAD",
            "Namibia Dollar",
            "$",
            R.drawable.flag_nad
        ),
        Currency(
            "NGN",
            "Nigeria Naira",
            "₦",
            R.drawable.flag_ngn
        ),
        Currency(
            "NIO",
            "Nicaragua Cordoba",
            "C$",
            R.drawable.flag_nio
        ),
        Currency(
            "NOK",
            "Norway Krone",
            "kr",
            R.drawable.flag_nok
        ),
        Currency(
            "NPR",
            "Nepal Rupee",
            "₨",
            R.drawable.flag_npr
        ),
        Currency(
            "NZD",
            "New Zealand Dollar",
            "$",
            R.drawable.flag_nzd
        ),
        Currency(
            "OMR",
            "Oman Rial",
            "﷼",
            R.drawable.flag_omr
        ),
        Currency(
            "PEN",
            "Peru Sol",
            "S/.",
            R.drawable.flag_pen
        ),
        Currency(
            "PGK",
            "Papua New Guinean Kina",
            "K",
            R.drawable.flag_pgk
        ),
        Currency(
            "PHP",
            "Philippines Peso",
            "₱",
            R.drawable.flag_php
        ),
        Currency(
            "PKR",
            "Pakistan Rupee",
            "₨",
            R.drawable.flag_pkr
        ),
        Currency(
            "PLN",
            "Poland Zloty",
            "zł",
            R.drawable.flag_pln
        ),
        Currency(
            "PYG",
            "Paraguay Guarani",
            "Gs",
            R.drawable.flag_pyg
        ),
        Currency(
            "QAR",
            "Qatar Riyal",
            "﷼",
            R.drawable.flag_qar
        ),
        Currency(
            "RON",
            "Romania Leu",
            "lei",
            R.drawable.flag_ron
        ),
        Currency(
            "RSD",
            "Serbia Dinar",
            "Дин.",
            R.drawable.flag_rsd
        ),
        Currency(
            "RUB",
            "Russia Ruble",
            "₽",
            R.drawable.flag_rub
        ),
        Currency(
            "SAR",
            "Saudi Arabia Riyal",
            "﷼",
            R.drawable.flag_sar
        ),
        Currency(
            "SEK",
            "Sweden Krona",
            "kr",
            R.drawable.flag_sek
        ),
        Currency(
            "SGD",
            "Singapore Dollar",
            "$",
            R.drawable.flag_sgd
        ),
        Currency(
            "SOS",
            "Somalia Shilling",
            "S",
            R.drawable.flag_sos
        ),
        Currency(
            "SRD",
            "Suriname Dollar",
            "$",
            R.drawable.flag_srd
        ),
        Currency(
            "THB",
            "Thailand Baht",
            "฿",
            R.drawable.flag_thb
        ),
        Currency(
            "TTD",
            "Trinidad and Tobago Dollar",
            "TT$",
            R.drawable.flag_ttd
        ),
        Currency(
            "TWD",
            "Taiwan New Dollar",
            "NT$",
            R.drawable.flag_twd
        ),
        Currency(
            "TZS",
            "Tanzanian Shilling",
            "TSh",
            R.drawable.flag_tzs
        ),
        Currency(
            "UAH",
            "Ukraine Hryvnia",
            "₴",
            R.drawable.flag_uah
        ),
        Currency(
            "UGX",
            "Ugandan Shilling",
            "USh",
            R.drawable.flag_ugx
        ),
        Currency(
            "UYU",
            "Uruguay Peso",
            "\$U",
            R.drawable.flag_uyu
        ),
        Currency(
            "VEF",
            "Venezuela Bolívar",
            "Bs",
            R.drawable.flag_vef
        ),
        Currency(
            "VND",
            "Viet Nam Dong",
            "₫",
            R.drawable.flag_vnd
        ),
        Currency(
            "YER",
            "Yemen Rial",
            "﷼",
            R.drawable.flag_yer
        ),
        Currency(
            "ZAR",
            "South Africa Rand",
            "R",
            R.drawable.flag_zar
        )
    )

    /*
     *      GENERIC STATIC FUNCTIONS
     */

    private var allCurrenciesList: List<Currency>? = null

    val allCurrencies: List<Currency>?
        get() {
            if (allCurrenciesList == null) {
                allCurrenciesList = Arrays.asList<Currency>(*CURRENCIES)
            }
            return allCurrenciesList
        }

//    val allCurrencies: List<Currency>?
//        get() {
//            fun getAllCurrencies(): List<Currency> {
//                if (allCurrenciesList == null) {
//                    allCurrenciesList = Arrays.asList<Currency>(*CURRENCIES)
//                }
//                return allCurrenciesList
//            }
//        }

    fun getCurrencyByISO(currencyIsoCode: String): Currency? {
        // Because the data we have is sorted by ISO codes and not by names, we must check all
        // currencies one by one

        for (c in CURRENCIES) {
            if (currencyIsoCode == c.code) {
                return c
            }
        }
        return null
    }

    fun getCurrencyByName(currencyName: String): Currency? {
        // Because the data we have is sorted by ISO codes and not by names, we must check all
        // currencies one by one

        for (c in CURRENCIES) {
            if (currencyName == c.name) {
                return c
            }
        }
        return null
    }

    fun getCurrencyByCode(code: String): Currency? {
        for (c in CURRENCIES) {
            if (code == c.code) {
                return c
            }
        }
        return null
    }

    fun getCurrencyByCountryCode(countryCode: String): Currency? {
        // Because the data we have is sorted by ISO codes and not by names, we must check all
        // currencies one by one

        val locale = Locale("", countryCode)
        var currencyCode = ""
        try {
            val currency = java.util.Currency.getInstance(locale)
            if (currency != null) {
                currencyCode = currency.currencyCode
            }
        } catch (e: IllegalArgumentException) {

        } catch (e: NullPointerException) {

        }

        return if (!TextUtils.isEmpty(currencyCode)) {
            getCurrencyByCode(
                currencyCode
            )
        } else null

    }

    fun getFlagByCurrencyCode(context: Context, code: String): Int {
        try {
            return context.resources
                .getIdentifier(
                    "flag_" + code.toLowerCase(), "drawable",
                    context.packageName
                )
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

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
                val flag =
                    getFlagDrawable(
                        Utils.context!!,
                        flagResName
                    )
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
        return getFlagDrawable(
            Utils.context!!,
            countryIsoName
        )
    }

    fun getCountryName(countryCode: String): String {
        if (TextUtils.isEmpty(countryCode)) return ""
        val loc = Locale("", countryCode)
        return loc.displayCountry
    }

    fun getCurrencySymbol(currencyCode: String): String {
        val currency = java.util.Currency.getInstance(currencyCode)
        return currency.symbol
    }


}
