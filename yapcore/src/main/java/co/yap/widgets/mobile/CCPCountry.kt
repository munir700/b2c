package co.yap.widgets.mobile

import android.content.Context
import android.util.Log
import co.yap.yapcore.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.text.Collator
import java.util.*

class CCPCountry : Comparable<CCPCountry> {
    lateinit var nameCode: String
    lateinit var phoneCode: String
    lateinit var name: String
    var englishName: String? = null
    internal var flagResID = DEFAULT_FLAG_RES

    val flagID: Int
        get() {
            if (flagResID == -99) {
                flagResID = getFlagMasterResID(this)
            }
            return flagResID
        }

    constructor() {

    }

    fun getphoneCode(): String {
        return phoneCode
        var name: String = "971"
        if (null != phoneCode && ::phoneCode.isInitialized) {
            name = phoneCode
        }
        return name
    }

    fun getnameCode(): String {
        var name: String = "ae"
        if (null != nameCode && ::nameCode.isInitialized) {
            name = nameCode
        }
        return name
    }

    constructor(nameCode: String, phoneCode: String, name: String, flagResID: Int) {
        this.nameCode = nameCode.toUpperCase(Locale.ROOT)
        this.phoneCode = phoneCode
        this.name = name
        this.flagResID = flagResID
    }

    fun log() {
        try {
            Log.d(TAG, "Country->$nameCode:$phoneCode:$name")
        } catch (ex: NullPointerException) {
            Log.d(TAG, "Null")
        }

    }

    internal fun logString(): String {
        return nameCode.toUpperCase() + " +" + phoneCode + "(" + name + ")"
    }

    internal fun isEligibleForQuery(query: String): Boolean {
        var query = query
        query = query.toLowerCase()
        return name.toLowerCase().contains(query) || nameCode.toLowerCase().contains(query) || phoneCode.toLowerCase().contains(
            query
        ) || getEnglishName(this)!!.toLowerCase().contains(query)
    }

    override fun compareTo(o: CCPCountry): Int {
        return Collator.getInstance().compare(name, o.name)
    }

    companion object {

        internal var DEFAULT_FLAG_RES = -99
        internal var TAG = "Class Country"
        internal var loadedLibraryMasterListLanguage: CountryCodePicker.Language? = null
        internal var dialogTitle: String? = null
        internal var searchHintMessage: String? = null
        internal var noResultFoundAckMessage: String? = null
        var loadedLibraryMaterList: List<CCPCountry>? = null
            internal set
        //countries with +1
        private val ANTIGUA_AND_BARBUDA_AREA_CODES = "268"
        private val ANGUILLA_AREA_CODES = "264"
        private val BARBADOS_AREA_CODES = "246"
        private val BERMUDA_AREA_CODES = "441"
        private val BAHAMAS_AREA_CODES = "242"
        private val CANADA_AREA_CODES =
            "204/226/236/249/250/289/306/343/365/403/416/418/431/437/438/450/506/514/519/579/581/587/600/604/613/639/647/705/709/769/778/780/782/807/819/825/867/873/902/905/"
        private val DOMINICA_AREA_CODES = "767"
        private val DOMINICAN_REPUBLIC_AREA_CODES = "809/829/849"
        private val GRENADA_AREA_CODES = "473"
        private val JAMAICA_AREA_CODES = "876"
        private val SAINT_KITTS_AND_NEVIS_AREA_CODES = "869"
        private val CAYMAN_ISLANDS_AREA_CODES = "345"
        private val SAINT_LUCIA_AREA_CODES = "758"
        private val MONTSERRAT_AREA_CODES = "664"
        private val PUERTO_RICO_AREA_CODES = "787"
        private val SINT_MAARTEN_AREA_CODES = "721"
        private val TURKS_AND_CAICOS_ISLANDS_AREA_CODES = "649"
        private val TRINIDAD_AND_TOBAGO_AREA_CODES = "868"
        private val SAINT_VINCENT_AND_THE_GRENADINES_AREA_CODES = "784"
        private val BRITISH_VIRGIN_ISLANDS_AREA_CODES = "284"
        private val US_VIRGIN_ISLANDS_AREA_CODES = "340"


        public fun getCountryForCode(
            context: Context,
            language: CountryCodePicker.Language,
            preferredCountries: List<CCPCountry>,
            code: Int
        ): CCPCountry? {
            return getCountryForCode(context, language, preferredCountries, code.toString() + "")
        }

        public fun getCountryForCode(
            context: Context,
            language: CountryCodePicker.Language,
            preferredCountries: List<CCPCountry>?,
            code: String
        ): CCPCountry? {

            if (preferredCountries != null && !preferredCountries.isEmpty()) {
                for (CCPCountry in preferredCountries) {
                    if (CCPCountry.phoneCode == code) {
                        return CCPCountry
                    }
                }
            }

            for (CCPCountry in getLibraryMasterCountryList(context, language)!!) {
                if (CCPCountry.phoneCode == code) {
                    return CCPCountry
                }
            }
            return null
        }

        internal fun loadDataFromXML(context: Context, language: CountryCodePicker.Language) {
            var countries: MutableList<CCPCountry> = ArrayList()
            var tempDialogTitle = ""
            var tempSearchHint = ""
            var tempNoResultAck = ""
            try {
                val xmlFactoryObject = XmlPullParserFactory.newInstance()
                val xmlPullParser = xmlFactoryObject.newPullParser()
                val ins = context.resources.openRawResource(
                    context.resources
                        .getIdentifier(
                            "ccp_" + language.toString().toLowerCase(Locale.ROOT),
                            "raw", context.packageName
                        )
                )
                xmlPullParser.setInput(ins, "UTF-8")
                var event = xmlPullParser.eventType
                while (event != XmlPullParser.END_DOCUMENT) {
                    val name = xmlPullParser.name
                    when (event) {
                        XmlPullParser.START_TAG -> {
                        }
                        XmlPullParser.END_TAG -> if (name == "country") {
                            val ccpCountry = CCPCountry()
                            ccpCountry.nameCode = xmlPullParser.getAttributeValue(null, "name_code").toUpperCase()
                            ccpCountry.phoneCode = xmlPullParser.getAttributeValue(null, "phone_code")
                            Companion.setEnglishName(ccpCountry, xmlPullParser.getAttributeValue(null, "english_name"))

                            ccpCountry.name = xmlPullParser.getAttributeValue(null, "name")
                            countries.add(ccpCountry)
                        } else if (name == "ccp_dialog_title") {
                            tempDialogTitle = xmlPullParser.getAttributeValue(null, "translation")
                        } else if (name == "ccp_dialog_search_hint_message") {
                            tempSearchHint = xmlPullParser.getAttributeValue(null, "translation")
                        } else if (name == "ccp_dialog_no_result_ack_message") {
                            tempNoResultAck = xmlPullParser.getAttributeValue(null, "translation")
                        }
                    }
                    event = xmlPullParser.next()
                }
                loadedLibraryMasterListLanguage = language
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }

            if (countries.size == 0) {
                loadedLibraryMasterListLanguage = CountryCodePicker.Language.ENGLISH
                countries = libraryMasterCountriesEnglish
            }

            dialogTitle = if (tempDialogTitle.length > 0) tempDialogTitle else "Select a country"
            searchHintMessage = if (tempSearchHint.length > 0) tempSearchHint else "Search..."
            noResultFoundAckMessage = if (tempNoResultAck.length > 0) tempNoResultAck else "Results not found"
            loadedLibraryMaterList = countries

            // sort list
            Collections.sort(loadedLibraryMaterList)
        }

        fun getDialogTitle(context: Context, language: CountryCodePicker.Language): String? {
            if (loadedLibraryMasterListLanguage == null || loadedLibraryMasterListLanguage !== language || dialogTitle == null || dialogTitle!!.length == 0) {
                loadDataFromXML(context, language)
            }
            return dialogTitle
        }

        fun getSearchHintMessage(context: Context, language: CountryCodePicker.Language): String? {
            if (loadedLibraryMasterListLanguage == null || loadedLibraryMasterListLanguage !== language || searchHintMessage == null || searchHintMessage!!.length == 0) {
                loadDataFromXML(context, language)
            }
            return searchHintMessage
        }

        fun getNoResultFoundAckMessage(context: Context, language: CountryCodePicker.Language): String? {
            if (loadedLibraryMasterListLanguage == null || loadedLibraryMasterListLanguage !== language || noResultFoundAckMessage == null || noResultFoundAckMessage!!.length == 0) {
                loadDataFromXML(context, language)
            }
            return noResultFoundAckMessage
        }

        fun setDialogTitle(dialogTitle: String) {
            CCPCountry.dialogTitle = dialogTitle
        }

        fun setSearchHintMessage(searchHintMessage: String) {
            CCPCountry.searchHintMessage = searchHintMessage
        }

        fun setNoResultFoundAckMessage(noResultFoundAckMessage: String) {
            CCPCountry.noResultFoundAckMessage = noResultFoundAckMessage
        }

        internal fun getCountryForCodeFromEnglishList(code: String): CCPCountry? {

            val countries: List<CCPCountry>
            countries = libraryMasterCountriesEnglish

            for (ccpCountry in countries) {
                if (ccpCountry.phoneCode == code) {
                    return ccpCountry
                }
            }
            return null
        }

        internal fun getCustomMasterCountryList(context: Context, codePicker: CountryCodePicker): List<CCPCountry>? {
            codePicker.refreshCustomMasterList()
            return if (codePicker.customMasterCountriesList != null && codePicker.customMasterCountriesList!!.size > 0) {
                codePicker.customMasterCountriesList
            } else {
                getLibraryMasterCountryList(context, codePicker.getLanguageToApply()!!)
            }
        }


        internal fun getCountryForNameCodeFromCustomMasterList(
            context: Context,
            customMasterCountriesList: List<CCPCountry>?,
            language: CountryCodePicker.Language,
            nameCode: String
        ): CCPCountry? {
            if (customMasterCountriesList == null || customMasterCountriesList.size == 0) {
                return getCountryForNameCodeFromLibraryMasterList(context, language, nameCode)
            } else {
                for (ccpCountry in customMasterCountriesList) {
                    if (ccpCountry.nameCode.equals(nameCode, ignoreCase = true)) {
                        return ccpCountry
                    }
                }
            }
            return null
        }


        fun getCountryForNameCodeFromLibraryMasterList(
            context: Context,
            language: CountryCodePicker.Language,
            nameCode: String
        ): CCPCountry? {
            val countries: List<CCPCountry>?
            countries = CCPCountry.getLibraryMasterCountryList(context, language)
            for (ccpCountry in countries!!) {
                if (ccpCountry.nameCode.equals(nameCode, ignoreCase = true)) {
                    return ccpCountry
                }
            }
            return null
        }

        internal fun getCountryForNameCodeFromEnglishList(nameCode: String): CCPCountry? {
            val countries: List<CCPCountry>
            countries = libraryMasterCountriesEnglish
            for (CCPCountry in countries) {
                if (CCPCountry.nameCode.equals(nameCode, ignoreCase = true)) {
                    return CCPCountry
                }
            }
            return null
        }

        internal fun getCountryForNumber(
            context: Context,
            language: CountryCodePicker.Language,
            preferredCountries: List<CCPCountry>?,
            fullNumber: String
        ): CCPCountry? {
            val firstDigit: Int
            //String plainNumber = PhoneNumberUtil.getInstance().normalizeDigitsOnly(fullNumber);
            if (fullNumber.length != 0) {
                if (fullNumber[0] == '+') {
                    firstDigit = 1
                } else {
                    firstDigit = 0
                }
                var ccpCountry: CCPCountry? = null
                for (i in firstDigit..fullNumber.length) {
                    val code = fullNumber.substring(firstDigit, i)
                    var countryGroup: CCPCountryGroup? = null
                    try {
                        countryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(Integer.parseInt(code))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (countryGroup != null) {
                        val areaCodeStartsAt = firstDigit + code.length
                        //when phone number covers area code too.
                        if (fullNumber.length >= areaCodeStartsAt + countryGroup!!.areaCodeLength) {
                            val areaCode =
                                fullNumber.substring(areaCodeStartsAt, areaCodeStartsAt + countryGroup!!.areaCodeLength)
                            return countryGroup!!.getCountryForAreaCode(context, language, areaCode)
                        } else {
                            return getCountryForNameCodeFromLibraryMasterList(
                                context,
                                language,
                                countryGroup!!.defaultNameCode
                            )
                        }
                    } else {
                        ccpCountry = CCPCountry.getCountryForCode(context, language, preferredCountries, code)
                        if (ccpCountry != null) {
                            return ccpCountry
                        }
                    }
                }
            }
            //it reaches here means, phone number has some problem.
            return null
        }

        fun getCountryForNumber(
            context: Context,
            language: CountryCodePicker.Language,
            fullNumber: String
        ): CCPCountry? {
            return getCountryForNumber(context, language, null, fullNumber)
        }

        internal fun getFlagMasterResID(CCPCountry: CCPCountry): Int {
            when (CCPCountry.nameCode.toLowerCase()) {
                //this should be sorted based on country name code.
                "ad" //andorra
                -> return R.drawable.flag_andorra
                "ae" //united arab emirates
                -> return R.drawable.flag_uae
                "af" //afghanistan
                -> return R.drawable.flag_afghanistan
                "ag" //antigua & barbuda
                -> return R.drawable.flag_antigua_and_barbuda
                "ai" //anguilla // Caribbean Islands
                -> return R.drawable.flag_anguilla
                "al" //albania
                -> return R.drawable.flag_albania
                "am" //armenia
                -> return R.drawable.flag_armenia
                "ao" //angola
                -> return R.drawable.flag_angola
                "aq" //antarctica // custom
                -> return R.drawable.flag_antarctica
                "ar" //argentina
                -> return R.drawable.flag_argentina
                "as" //American Samoa
                -> return R.drawable.flag_american_samoa
                "at" //austria
                -> return R.drawable.flag_austria
                "au" //australia
                -> return R.drawable.flag_australia
                "aw" //aruba
                -> return R.drawable.flag_aruba
                "ax" //alan islands
                -> return R.drawable.flag_aland
                "az" //azerbaijan
                -> return R.drawable.flag_azerbaijan
                "ba" //bosnia and herzegovina
                -> return R.drawable.flag_bosnia
                "bb" //barbados
                -> return R.drawable.flag_barbados
                "bd" //bangladesh
                -> return R.drawable.flag_bangladesh
                "be" //belgium
                -> return R.drawable.flag_belgium
                "bf" //burkina faso
                -> return R.drawable.flag_burkina_faso
                "bg" //bulgaria
                -> return R.drawable.flag_bulgaria
                "bh" //bahrain
                -> return R.drawable.flag_bahrain
                "bi" //burundi
                -> return R.drawable.flag_burundi
                "bj" //benin
                -> return R.drawable.flag_benin
                "bl" //saint barthélemy
                -> return R.drawable.flag_saint_barthelemy// custom
                "bm" //bermuda
                -> return R.drawable.flag_bermuda
                "bn" //brunei darussalam // custom
                -> return R.drawable.flag_brunei
                "bo" //bolivia, plurinational state of
                -> return R.drawable.flag_bolivia
                "br" //brazil
                -> return R.drawable.flag_brazil
                "bs" //bahamas
                -> return R.drawable.flag_bahamas
                "bt" //bhutan
                -> return R.drawable.flag_bhutan
                "bw" //botswana
                -> return R.drawable.flag_botswana
                "by" //belarus
                -> return R.drawable.flag_belarus
                "bz" //belize
                -> return R.drawable.flag_belize
                "ca" //canada
                -> return R.drawable.flag_canada
                "cc" //cocos (keeling) islands
                -> return R.drawable.flag_cocos// custom
                "cd" //congo, the democratic republic of the
                -> return R.drawable.flag_democratic_republic_of_the_congo
                "cf" //central african republic
                -> return R.drawable.flag_central_african_republic
                "cg" //congo
                -> return R.drawable.flag_republic_of_the_congo
                "ch" //switzerland
                -> return R.drawable.flag_switzerland
                "ci" //côte d\'ivoire
                -> return R.drawable.flag_cote_divoire
                "ck" //cook islands
                -> return R.drawable.flag_cook_islands
                "cl" //chile
                -> return R.drawable.flag_chile
                "cm" //cameroon
                -> return R.drawable.flag_cameroon
                "cn" //china
                -> return R.drawable.flag_china
                "co" //colombia
                -> return R.drawable.flag_colombia
                "cr" //costa rica
                -> return R.drawable.flag_costa_rica
                "cu" //cuba
                -> return R.drawable.flag_cuba
                "cv" //cape verde
                -> return R.drawable.flag_cape_verde
                "cw" //curaçao
                -> return R.drawable.flag_curacao
                "cx" //christmas island
                -> return R.drawable.flag_christmas_island
                "cy" //cyprus
                -> return R.drawable.flag_cyprus
                "cz" //czech republic
                -> return R.drawable.flag_czech_republic
                "de" //germany
                -> return R.drawable.flag_germany
                "dj" //djibouti
                -> return R.drawable.flag_djibouti
                "dk" //denmark
                -> return R.drawable.flag_denmark
                "dm" //dominica
                -> return R.drawable.flag_dominica
                "do" //dominican republic
                -> return R.drawable.flag_dominican_republic
                "dz" //algeria
                -> return R.drawable.flag_algeria
                "ec" //ecuador
                -> return R.drawable.flag_ecuador
                "ee" //estonia
                -> return R.drawable.flag_estonia
                "eg" //egypt
                -> return R.drawable.flag_egypt
                "er" //eritrea
                -> return R.drawable.flag_eritrea
                "es" //spain
                -> return R.drawable.flag_spain
                "et" //ethiopia
                -> return R.drawable.flag_ethiopia
                "fi" //finland
                -> return R.drawable.flag_finland
                "fj" //fiji
                -> return R.drawable.flag_fiji
                "fk" //falkland islands (malvinas)
                -> return R.drawable.flag_falkland_islands
                "fm" //micronesia, federated states of
                -> return R.drawable.flag_micronesia
                "fo" //faroe islands
                -> return R.drawable.flag_faroe_islands
                "fr" //france
                -> return R.drawable.flag_france
                "ga" //gabon
                -> return R.drawable.flag_gabon
                "gb" //united kingdom
                -> return R.drawable.flag_united_kingdom
                "gd" //grenada
                -> return R.drawable.flag_grenada
                "ge" //georgia
                -> return R.drawable.flag_georgia
                "gf" //guyane
                -> return R.drawable.flag_guyane
                "gg" //Guernsey
                -> return R.drawable.flag_guernsey
                "gh" //ghana
                -> return R.drawable.flag_ghana
                "gi" //gibraltar
                -> return R.drawable.flag_gibraltar
                "gl" //greenland
                -> return R.drawable.flag_greenland
                "gm" //gambia
                -> return R.drawable.flag_gambia
                "gn" //guinea
                -> return R.drawable.flag_guinea
                "gp" //guadeloupe
                -> return R.drawable.flag_guadeloupe
                "gq" //equatorial guinea
                -> return R.drawable.flag_equatorial_guinea
                "gr" //greece
                -> return R.drawable.flag_greece
                "gt" //guatemala
                -> return R.drawable.flag_guatemala
                "gu" //Guam
                -> return R.drawable.flag_guam
                "gw" //guinea-bissau
                -> return R.drawable.flag_guinea_bissau
                "gy" //guyana
                -> return R.drawable.flag_guyana
                "hk" //hong kong
                -> return R.drawable.flag_hong_kong
                "hn" //honduras
                -> return R.drawable.flag_honduras
                "hr" //croatia
                -> return R.drawable.flag_croatia
                "ht" //haiti
                -> return R.drawable.flag_haiti
                "hu" //hungary
                -> return R.drawable.flag_hungary
                "id" //indonesia
                -> return R.drawable.flag_indonesia
                "ie" //ireland
                -> return R.drawable.flag_ireland
                "il" //israel
                -> return R.drawable.flag_israel
                "im" //isle of man
                -> return R.drawable.flag_isleof_man // custom
                "is" //Iceland
                -> return R.drawable.flag_iceland
                "in" //india
                -> return R.drawable.flag_india
                "io" //British indian ocean territory
                -> return R.drawable.flag_british_indian_ocean_territory
                "iq" //iraq
                -> return R.drawable.flag_iraq_new
                "ir" //iran, islamic republic of
                -> return R.drawable.flag_iran
                "it" //italy
                -> return R.drawable.flag_italy
                "je" //Jersey
                -> return R.drawable.flag_jersey
                "jm" //jamaica
                -> return R.drawable.flag_jamaica
                "jo" //jordan
                -> return R.drawable.flag_jordan
                "jp" //japan
                -> return R.drawable.flag_japan
                "ke" //kenya
                -> return R.drawable.flag_kenya
                "kg" //kyrgyzstan
                -> return R.drawable.flag_kyrgyzstan
                "kh" //cambodia
                -> return R.drawable.flag_cambodia
                "ki" //kiribati
                -> return R.drawable.flag_kiribati
                "km" //comoros
                -> return R.drawable.flag_comoros
                "kn" //st kitts & nevis
                -> return R.drawable.flag_saint_kitts_and_nevis
                "kp" //north korea
                -> return R.drawable.flag_north_korea
                "kr" //south korea
                -> return R.drawable.flag_south_korea
                "kw" //kuwait
                -> return R.drawable.flag_kuwait
                "ky" //Cayman_Islands
                -> return R.drawable.flag_cayman_islands
                "kz" //kazakhstan
                -> return R.drawable.flag_kazakhstan
                "la" //lao people\'s democratic republic
                -> return R.drawable.flag_laos
                "lb" //lebanon
                -> return R.drawable.flag_lebanon
                "lc" //st lucia
                -> return R.drawable.flag_saint_lucia
                "li" //liechtenstein
                -> return R.drawable.flag_liechtenstein
                "lk" //sri lanka
                -> return R.drawable.flag_sri_lanka
                "lr" //liberia
                -> return R.drawable.flag_liberia
                "ls" //lesotho
                -> return R.drawable.flag_lesotho
                "lt" //lithuania
                -> return R.drawable.flag_lithuania
                "lu" //luxembourg
                -> return R.drawable.flag_luxembourg
                "lv" //latvia
                -> return R.drawable.flag_latvia
                "ly" //libya
                -> return R.drawable.flag_libya
                "ma" //morocco
                -> return R.drawable.flag_morocco
                "mc" //monaco
                -> return R.drawable.flag_monaco
                "md" //moldova, republic of
                -> return R.drawable.flag_moldova
                "me" //montenegro
                -> return R.drawable.flag_of_montenegro// custom
                "mf" -> return R.drawable.flag_saint_martin
                "mg" //madagascar
                -> return R.drawable.flag_madagascar
                "mh" //marshall islands
                -> return R.drawable.flag_marshall_islands
                "mk" //macedonia, the former yugoslav republic of
                -> return R.drawable.flag_macedonia
                "ml" //mali
                -> return R.drawable.flag_mali
                "mm" //myanmar
                -> return R.drawable.flag_myanmar
                "mn" //mongolia
                -> return R.drawable.flag_mongolia
                "mo" //macao
                -> return R.drawable.flag_macao
                "mp" // Northern mariana islands
                -> return R.drawable.flag_northern_mariana_islands
                "mq" //martinique
                -> return R.drawable.flag_martinique
                "mr" //mauritania
                -> return R.drawable.flag_mauritania
                "ms" //montserrat
                -> return R.drawable.flag_montserrat
                "mt" //malta
                -> return R.drawable.flag_malta
                "mu" //mauritius
                -> return R.drawable.flag_mauritius
                "mv" //maldives
                -> return R.drawable.flag_maldives
                "mw" //malawi
                -> return R.drawable.flag_malawi
                "mx" //mexico
                -> return R.drawable.flag_mexico
                "my" //malaysia
                -> return R.drawable.flag_malaysia
                "mz" //mozambique
                -> return R.drawable.flag_mozambique
                "na" //namibia
                -> return R.drawable.flag_namibia
                "nc" //new caledonia
                -> return R.drawable.flag_new_caledonia// custom
                "ne" //niger
                -> return R.drawable.flag_niger
                "nf" //Norfolk
                -> return R.drawable.flag_norfolk_island
                "ng" //nigeria
                -> return R.drawable.flag_nigeria
                "ni" //nicaragua
                -> return R.drawable.flag_nicaragua
                "nl" //netherlands
                -> return R.drawable.flag_netherlands
                "no" //norway
                -> return R.drawable.flag_norway
                "np" //nepal
                -> return R.drawable.flag_nepal
                "nr" //nauru
                -> return R.drawable.flag_nauru
                "nu" //niue
                -> return R.drawable.flag_niue
                "nz" //new zealand
                -> return R.drawable.flag_new_zealand
                "om" //oman
                -> return R.drawable.flag_oman
                "pa" //panama
                -> return R.drawable.flag_panama
                "pe" //peru
                -> return R.drawable.flag_peru
                "pf" //french polynesia
                -> return R.drawable.flag_french_polynesia
                "pg" //papua new guinea
                -> return R.drawable.flag_papua_new_guinea
                "ph" //philippines
                -> return R.drawable.flag_philippines
                "pk" //pakistan
                -> return R.drawable.flag_pakistan
                "pl" //poland
                -> return R.drawable.flag_poland
                "pm" //saint pierre and miquelon
                -> return R.drawable.flag_saint_pierre
                "pn" //pitcairn
                -> return R.drawable.flag_pitcairn_islands
                "pr" //puerto rico
                -> return R.drawable.flag_puerto_rico
                "ps" //palestine
                -> return R.drawable.flag_palestine
                "pt" //portugal
                -> return R.drawable.flag_portugal
                "pw" //palau
                -> return R.drawable.flag_palau
                "py" //paraguay
                -> return R.drawable.flag_paraguay
                "qa" //qatar
                -> return R.drawable.flag_qatar
                "re" //la reunion
                -> return R.drawable.flag_martinique // no exact flag found
                "ro" //romania
                -> return R.drawable.flag_romania
                "rs" //serbia
                -> return R.drawable.flag_serbia // custom
                "ru" //russian federation
                -> return R.drawable.flag_russian_federation
                "rw" //rwanda
                -> return R.drawable.flag_rwanda
                "sa" //saudi arabia
                -> return R.drawable.flag_saudi_arabia
                "sb" //solomon islands
                -> return R.drawable.flag_soloman_islands
                "sc" //seychelles
                -> return R.drawable.flag_seychelles
                "sd" //sudan
                -> return R.drawable.flag_sudan
                "se" //sweden
                -> return R.drawable.flag_sweden
                "sg" //singapore
                -> return R.drawable.flag_singapore
                "sh" //saint helena, ascension and tristan da cunha
                -> return R.drawable.flag_saint_helena // custom
                "si" //slovenia
                -> return R.drawable.flag_slovenia
                "sk" //slovakia
                -> return R.drawable.flag_slovakia
                "sl" //sierra leone
                -> return R.drawable.flag_sierra_leone
                "sm" //san marino
                -> return R.drawable.flag_san_marino
                "sn" //senegal
                -> return R.drawable.flag_senegal
                "so" //somalia
                -> return R.drawable.flag_somalia
                "sr" //suriname
                -> return R.drawable.flag_suriname
                "ss" //south sudan
                -> return R.drawable.flag_south_sudan
                "st" //sao tome and principe
                -> return R.drawable.flag_sao_tome_and_principe
                "sv" //el salvador
                -> return R.drawable.flag_el_salvador
                "sx" //sint maarten
                -> return R.drawable.flag_sint_maarten
                "sy" //syrian arab republic
                -> return R.drawable.flag_syria
                "sz" //swaziland
                -> return R.drawable.flag_swaziland
                "tc" //turks & caicos islands
                -> return R.drawable.flag_turks_and_caicos_islands
                "td" //chad
                -> return R.drawable.flag_chad
                "tg" //togo
                -> return R.drawable.flag_togo
                "th" //thailand
                -> return R.drawable.flag_thailand
                "tj" //tajikistan
                -> return R.drawable.flag_tajikistan
                "tk" //tokelau
                -> return R.drawable.flag_tokelau // custom
                "tl" //timor-leste
                -> return R.drawable.flag_timor_leste
                "tm" //turkmenistan
                -> return R.drawable.flag_turkmenistan
                "tn" //tunisia
                -> return R.drawable.flag_tunisia
                "to" //tonga
                -> return R.drawable.flag_tonga
                "tr" //turkey
                -> return R.drawable.flag_turkey
                "tt" //trinidad & tobago
                -> return R.drawable.flag_trinidad_and_tobago
                "tv" //tuvalu
                -> return R.drawable.flag_tuvalu
                "tw" //taiwan, province of china
                -> return R.drawable.flag_taiwan
                "tz" //tanzania, united republic of
                -> return R.drawable.flag_tanzania
                "ua" //ukraine
                -> return R.drawable.flag_ukraine
                "ug" //uganda
                -> return R.drawable.flag_uganda
                "us" //united states
                -> return R.drawable.flag_united_states_of_america
                "uy" //uruguay
                -> return R.drawable.flag_uruguay
                "uz" //uzbekistan
                -> return R.drawable.flag_uzbekistan
                "va" //holy see (vatican city state)
                -> return R.drawable.flag_vatican_city
                "vc" //st vincent & the grenadines
                -> return R.drawable.flag_saint_vicent_and_the_grenadines
                "ve" //venezuela, bolivarian republic of
                -> return R.drawable.flag_venezuela
                "vg" //british virgin islands
                -> return R.drawable.flag_british_virgin_islands
                "vi" //us virgin islands
                -> return R.drawable.flag_us_virgin_islands
                "vn" //vietnam
                -> return R.drawable.flag_vietnam
                "vu" //vanuatu
                -> return R.drawable.flag_vanuatu
                "wf" //wallis and futuna
                -> return R.drawable.flag_wallis_and_futuna
                "ws" //samoa
                -> return R.drawable.flag_samoa
                "xk" //kosovo
                -> return R.drawable.flag_kosovo
                "ye" //yemen
                -> return R.drawable.flag_yemen
                "yt" //mayotte
                -> return R.drawable.flag_martinique // no exact flag found
                "za" //south africa
                -> return R.drawable.flag_south_africa
                "zm" //zambia
                -> return R.drawable.flag_zambia
                "zw" //zimbabwe
                -> return R.drawable.flag_zimbabwe
                else -> return R.drawable.flag_transparent
            }
        }


        internal fun getFlagEmoji(CCPCountry: CCPCountry): String {
            when (CCPCountry.nameCode.toLowerCase()) {
                //this should be sorted based on country name code.
                "ad" -> return "🇦🇩"
                "ae" -> return "🇦🇪"
                "af" -> return "🇦🇫"
                "ag" -> return "🇦🇬"
                "ai" -> return "🇦🇮"
                "al" -> return "🇦🇱"
                "am" -> return "🇦🇲"
                "ao" -> return "🇦🇴"
                "aq" -> return "🇦🇶"
                "ar" -> return "🇦🇷"
                "as" -> return "🇦🇸"
                "at" -> return "🇦🇹"
                "au" -> return "🇦🇺"
                "aw" -> return "🇦🇼"
                "ax" -> return "🇦🇽"
                "az" -> return "🇦🇿"
                "ba" -> return "🇧🇦"
                "bb" -> return "🇧🇧"
                "bd" -> return "🇧🇩"
                "be" -> return "🇧🇪"
                "bf" -> return "🇧🇫"
                "bg" -> return "🇧🇬"
                "bh" -> return "🇧🇭"
                "bi" -> return "🇧🇮"
                "bj" -> return "🇧🇯"
                "bl" -> return "🇧🇱"
                "bm" -> return "🇧🇲"
                "bn" -> return "🇧🇳"
                "bo" -> return "🇧🇴"
                "bq" -> return "🇧🇶"
                "br" -> return "🇧🇷"
                "bs" -> return "🇧🇸"
                "bt" -> return "🇧🇹"
                "bv" -> return "🇧🇻"
                "bw" -> return "🇧🇼"
                "by" -> return "🇧🇾"
                "bz" -> return "🇧🇿"
                "ca" -> return "🇨🇦"
                "cc" -> return "🇨🇨"
                "cd" -> return "🇨🇩"
                "cf" -> return "🇨🇫"
                "cg" -> return "🇨🇬"
                "ch" -> return "🇨🇭"
                "ci" -> return "🇨🇮"
                "ck" -> return "🇨🇰"
                "cl" -> return "🇨🇱"
                "cm" -> return "🇨🇲"
                "cn" -> return "🇨🇳"
                "co" -> return "🇨🇴"
                "cr" -> return "🇨🇷"
                "cu" -> return "🇨🇺"
                "cv" -> return "🇨🇻"
                "cw" -> return "🇨🇼"
                "cx" -> return "🇨🇽"
                "cy" -> return "🇨🇾"
                "cz" -> return "🇨🇿"
                "de" -> return "🇩🇪"
                "dj" -> return "🇩🇯"
                "dk" -> return "🇩🇰"
                "dm" -> return "🇩🇲"
                "do" -> return "🇩🇴"
                "dz" -> return "🇩🇿"
                "ec" -> return "🇪🇨"
                "ee" -> return "🇪🇪"
                "eg" -> return "🇪🇬"
                "eh" -> return "🇪🇭"
                "er" -> return "🇪🇷"
                "es" -> return "🇪🇸"
                "et" -> return "🇪🇹"
                "fi" -> return "🇫🇮"
                "fj" -> return "🇫🇯"
                "fk" -> return "🇫🇰"
                "fm" -> return "🇫🇲"
                "fo" -> return "🇫🇴"
                "fr" -> return "🇫🇷"
                "ga" -> return "🇬🇦"
                "gb" -> return "🇬🇧"
                "gd" -> return "🇬🇩"
                "ge" -> return "🇬🇪"
                "gf" -> return "🇬🇫"
                "gg" -> return "🇬🇬"
                "gh" -> return "🇬🇭"
                "gi" -> return "🇬🇮"
                "gl" -> return "🇬🇱"
                "gm" -> return "🇬🇲"
                "gn" -> return "🇬🇳"
                "gp" -> return "🇬🇵"
                "gq" -> return "🇬🇶"
                "gr" -> return "🇬🇷"
                "gs" -> return "🇬🇸"
                "gt" -> return "🇬🇹"
                "gu" -> return "🇬🇺"
                "gw" -> return "🇬🇼"
                "gy" -> return "🇬🇾"
                "hk" -> return "🇭🇰"
                "hm" -> return "🇭🇲"
                "hn" -> return "🇭🇳"
                "hr" -> return "🇭🇷"
                "ht" -> return "🇭🇹"
                "hu" -> return "🇭🇺"
                "id" -> return "🇮🇩"
                "ie" -> return "🇮🇪"
                "il" -> return "🇮🇱"
                "im" -> return "🇮🇲"
                "in" -> return "🇮🇳"
                "io" -> return "🇮🇴"
                "iq" -> return "🇮🇶"
                "ir" -> return "🇮🇷"
                "is" -> return "🇮🇸"
                "it" -> return "🇮🇹"
                "je" -> return "🇯🇪"
                "jm" -> return "🇯🇲"
                "jo" -> return "🇯🇴"
                "jp" -> return "🇯🇵"
                "ke" -> return "🇰🇪"
                "kg" -> return "🇰🇬"
                "kh" -> return "🇰🇭"
                "ki" -> return "🇰🇮"
                "km" -> return "🇰🇲"
                "kn" -> return "🇰🇳"
                "kp" -> return "🇰🇵"
                "kr" -> return "🇰🇷"
                "kw" -> return "🇰🇼"
                "ky" -> return "🇰🇾"
                "kz" -> return "🇰🇿"
                "la" -> return "🇱🇦"
                "lb" -> return "🇱🇧"
                "lc" -> return "🇱🇨"
                "li" -> return "🇱🇮"
                "lk" -> return "🇱🇰"
                "lr" -> return "🇱🇷"
                "ls" -> return "🇱🇸"
                "lt" -> return "🇱🇹"
                "lu" -> return "🇱🇺"
                "lv" -> return "🇱🇻"
                "ly" -> return "🇱🇾"
                "ma" -> return "🇲🇦"
                "mc" -> return "🇲🇨"
                "md" -> return "🇲🇩"
                "me" -> return "🇲🇪"
                "mf" -> return "🇲🇫"
                "mg" -> return "🇲🇬"
                "mh" -> return "🇲🇭"
                "mk" -> return "🇲🇰"
                "ml" -> return "🇲🇱"
                "mm" -> return "🇲🇲"
                "mn" -> return "🇲🇳"
                "mo" -> return "🇲🇴"
                "mp" -> return "🇲🇵"
                "mq" -> return "🇲🇶"
                "mr" -> return "🇲🇷"
                "ms" -> return "🇲🇸"
                "mt" -> return "🇲🇹"
                "mu" -> return "🇲🇺"
                "mv" -> return "🇲🇻"
                "mw" -> return "🇲🇼"
                "mx" -> return "🇲🇽"
                "my" -> return "🇲🇾"
                "mz" -> return "🇲🇿"
                "na" -> return "🇳🇦"
                "nc" -> return "🇳🇨"
                "ne" -> return "🇳🇪"
                "nf" -> return "🇳🇫"
                "ng" -> return "🇳🇬"
                "ni" -> return "🇳🇮"
                "nl" -> return "🇳🇱"
                "no" -> return "🇳🇴"
                "np" -> return "🇳🇵"
                "nr" -> return "🇳🇷"
                "nu" -> return "🇳🇺"
                "nz" -> return "🇳🇿"
                "om" -> return "🇴🇲"
                "pa" -> return "🇵🇦"
                "pe" -> return "🇵🇪"
                "pf" -> return "🇵🇫"
                "pg" -> return "🇵🇬"
                "ph" -> return "🇵🇭"
                "pk" -> return "🇵🇰"
                "pl" -> return "🇵🇱"
                "pm" -> return "🇵🇲"
                "pn" -> return "🇵🇳"
                "pr" -> return "🇵🇷"
                "ps" -> return "🇵🇸"
                "pt" -> return "🇵🇹"
                "pw" -> return "🇵🇼"
                "py" -> return "🇵🇾"
                "qa" -> return "🇶🇦"
                "re" -> return "🇷🇪"
                "ro" -> return "🇷🇴"
                "rs" -> return "🇷🇸"
                "ru" -> return "🇷🇺"
                "rw" -> return "🇷🇼"
                "sa" -> return "🇸🇦"
                "sb" -> return "🇸🇧"
                "sc" -> return "🇸🇨"
                "sd" -> return "🇸🇩"
                "se" -> return "🇸🇪"
                "sg" -> return "🇸🇬"
                "sh" -> return "🇸🇭"
                "si" -> return "🇸🇮"
                "sj" -> return "🇸🇯"
                "sk" -> return "🇸🇰"
                "sl" -> return "🇸🇱"
                "sm" -> return "🇸🇲"
                "sn" -> return "🇸🇳"
                "so" -> return "🇸🇴"
                "sr" -> return "🇸🇷"
                "ss" -> return "🇸🇸"
                "st" -> return "🇸🇹"
                "sv" -> return "🇸🇻"
                "sx" -> return "🇸🇽"
                "sy" -> return "🇸🇾"
                "sz" -> return "🇸🇿"
                "tc" -> return "🇹🇨"
                "td" -> return "🇹🇩"
                "tf" -> return "🇹🇫"
                "tg" -> return "🇹🇬"
                "th" -> return "🇹🇭"
                "tj" -> return "🇹🇯"
                "tk" -> return "🇹🇰"
                "tl" -> return "🇹🇱"
                "tm" -> return "🇹🇲"
                "tn" -> return "🇹🇳"
                "to" -> return "🇹🇴"
                "tr" -> return "🇹🇷"
                "tt" -> return "🇹🇹"
                "tv" -> return "🇹🇻"
                "tw" -> return "🇹🇼"
                "tz" -> return "🇹🇿"
                "ua" -> return "🇺🇦"
                "ug" -> return "🇺🇬"
                "um" -> return "🇺🇲"
                "us" -> return "🇺🇸"
                "uy" -> return "🇺🇾"
                "uz" -> return "🇺🇿"
                "va" -> return "🇻🇦"
                "vc" -> return "🇻🇨"
                "ve" -> return "🇻🇪"
                "vg" -> return "🇻🇬"
                "vi" -> return "🇻🇮"
                "vn" -> return "🇻🇳"
                "vu" -> return "🇻🇺"
                "wf" -> return "🇼🇫"
                "ws" -> return "🇼🇸"
                "xk" -> return "🇽🇰"
                "ye" -> return "🇾🇪"
                "yt" -> return "🇾🇹"
                "za" -> return "🇿🇦"
                "zm" -> return "🇿🇲"
                "zw" -> return "🇿🇼"
                else -> return " "
            }
        }


        fun getLibraryMasterCountryList(context: Context, language: CountryCodePicker.Language): List<CCPCountry>? {
            if (loadedLibraryMasterListLanguage == null || language !== loadedLibraryMasterListLanguage || loadedLibraryMaterList == null || loadedLibraryMaterList!!.size == 0) { //when it is required to load country in country list
                loadDataFromXML(context, language)
            }
            return loadedLibraryMaterList
        }

        val libraryMasterCountriesEnglish: MutableList<CCPCountry>
            get() {
                val countries = ArrayList<CCPCountry>()
                countries.add(CCPCountry("ae", "971", "UAE", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("af", "93", "Afghanistan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ag", "1", "Antigua and Barbuda", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ai", "1", "Anguilla", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("al", "355", "Albania", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("am", "374", "Armenia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ao", "244", "Angola", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("aq", "672", "Antarctica", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ar", "54", "Argentina", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("as", "1", "American Samoa", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("at", "43", "Austria", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("au", "61", "Australia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("aw", "297", "Aruba", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ax", "358", "Åland Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("az", "994", "Azerbaijan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ba", "387", "Bosnia And Herzegovina", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bb", "1", "Barbados", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bd", "880", "Bangladesh", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("be", "32", "Belgium", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bf", "226", "Burkina Faso", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bg", "359", "Bulgaria", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bh", "973", "Bahrain", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bi", "257", "Burundi", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bj", "229", "Benin", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bl", "590", "Saint Barthélemy", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bm", "1", "Bermuda", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bn", "673", "Brunei Darussalam", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bo", "591", "Bolivia, Plurinational State Of", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("br", "55", "Brazil", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bs", "1", "Bahamas", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bt", "975", "Bhutan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bw", "267", "Botswana", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("by", "375", "Belarus", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("bz", "501", "Belize", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ca", "1", "Canada", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cc", "61", "Cocos (keeling) Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cd", "243", "Congo, The Democratic Republic Of The", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cf", "236", "Central African Republic", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cg", "242", "Congo", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ch", "41", "Switzerland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ci", "225", "Côte D'ivoire", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ck", "682", "Cook Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cl", "56", "Chile", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cm", "237", "Cameroon", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cn", "86", "China", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("co", "57", "Colombia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cr", "506", "Costa Rica", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cu", "53", "Cuba", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cv", "238", "Cape Verde", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cw", "599", "Curaçao", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cx", "61", "Christmas Island", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cy", "357", "Cyprus", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("cz", "420", "Czech Republic", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("de", "49", "Germany", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("dj", "253", "Djibouti", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("dk", "45", "Denmark", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("dm", "1", "Dominica", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("do", "1", "Dominican Republic", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("dz", "213", "Algeria", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ec", "593", "Ecuador", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ee", "372", "Estonia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("eg", "20", "Egypt", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("er", "291", "Eritrea", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("es", "34", "Spain", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("et", "251", "Ethiopia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("fi", "358", "Finland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("fj", "679", "Fiji", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("fk", "500", "Falkland Islands (malvinas)", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("fm", "691", "Micronesia, Federated States Of", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("fo", "298", "Faroe Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("fr", "33", "France", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ga", "241", "Gabon", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gb", "44", "United Kingdom", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gd", "1", "Grenada", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ge", "995", "Georgia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gf", "594", "French Guyana", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gh", "233", "Ghana", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gi", "350", "Gibraltar", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gl", "299", "Greenland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gm", "220", "Gambia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gn", "224", "Guinea", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gp", "450", "Guadeloupe", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gq", "240", "Equatorial Guinea", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gr", "30", "Greece", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gt", "502", "Guatemala", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gu", "1", "Guam", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gw", "245", "Guinea-bissau", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("gy", "592", "Guyana", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("hk", "852", "Hong Kong", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("hn", "504", "Honduras", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("hr", "385", "Croatia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ht", "509", "Haiti", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("hu", "36", "Hungary", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("id", "62", "Indonesia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ie", "353", "Ireland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("il", "972", "Israel", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("im", "44", "Isle Of Man", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("is", "354", "Iceland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("in", "91", "India", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("io", "246", "British Indian Ocean Territory", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("iq", "964", "Iraq", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ir", "98", "Iran, Islamic Republic Of", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("it", "39", "Italy", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("je", "44", "Jersey ", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("jm", "1", "Jamaica", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("jo", "962", "Jordan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("jp", "81", "Japan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ke", "254", "Kenya", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kg", "996", "Kyrgyzstan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kh", "855", "Cambodia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ki", "686", "Kiribati", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("km", "269", "Comoros", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kn", "1", "Saint Kitts and Nevis", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kp", "850", "North Korea", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kr", "82", "South Korea", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kw", "965", "Kuwait", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ky", "1", "Cayman Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("kz", "7", "Kazakhstan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("la", "856", "Lao People's Democratic Republic", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lb", "961", "Lebanon", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lc", "1", "Saint Lucia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("li", "423", "Liechtenstein", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lk", "94", "Sri Lanka", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lr", "231", "Liberia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ls", "266", "Lesotho", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lt", "370", "Lithuania", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lu", "352", "Luxembourg", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("lv", "371", "Latvia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ly", "218", "Libya", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ma", "212", "Morocco", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mc", "377", "Monaco", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("md", "373", "Moldova, Republic Of", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("me", "382", "Montenegro", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mf", "590", "Saint Martin", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mg", "261", "Madagascar", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mh", "692", "Marshall Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mk", "389", "Macedonia (FYROM)", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ml", "223", "Mali", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mm", "95", "Myanmar", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mn", "976", "Mongolia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mo", "853", "Macau", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mp", "1", "Northern Mariana Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mq", "596", "Martinique", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mr", "222", "Mauritania", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ms", "1", "Montserrat", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mt", "356", "Malta", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mu", "230", "Mauritius", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mv", "960", "Maldives", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mw", "265", "Malawi", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mx", "52", "Mexico", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("my", "60", "Malaysia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("mz", "258", "Mozambique", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("na", "264", "Namibia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("nc", "687", "New Caledonia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ne", "227", "Niger", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("nf", "672", "Norfolk Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ng", "234", "Nigeria", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ni", "505", "Nicaragua", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("nl", "31", "Netherlands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("no", "47", "Norway", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("np", "977", "Nepal", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("nr", "674", "Nauru", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("nu", "683", "Niue", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("nz", "64", "New Zealand", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("om", "968", "Oman", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pa", "507", "Panama", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pe", "51", "Peru", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pf", "689", "French Polynesia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pg", "675", "Papua New Guinea", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ph", "63", "Philippines", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pk", "92", "Pakistan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pl", "48", "Poland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pm", "508", "Saint Pierre And Miquelon", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pn", "870", "Pitcairn Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pr", "1", "Puerto Rico", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ps", "970", "Palestine", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pt", "351", "Portugal", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("pw", "680", "Palau", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("py", "595", "Paraguay", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("qa", "974", "Qatar", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("re", "262", "Réunion", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ro", "40", "Romania", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("rs", "381", "Serbia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ru", "7", "Russian Federation", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("rw", "250", "Rwanda", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sa", "966", "Saudi Arabia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sb", "677", "Solomon Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sc", "248", "Seychelles", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sd", "249", "Sudan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("se", "46", "Sweden", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sg", "65", "Singapore", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sh", "290", "Saint Helena, Ascension And Tristan Da Cunha", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("si", "386", "Slovenia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sk", "421", "Slovakia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sl", "232", "Sierra Leone", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sm", "378", "San Marino", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sn", "221", "Senegal", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("so", "252", "Somalia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sr", "597", "Suriname", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ss", "211", "South Sudan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("st", "239", "Sao Tome And Principe", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sv", "503", "El Salvador", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sx", "1", "Sint Maarten", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sy", "963", "Syrian Arab Republic", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("sz", "268", "Swaziland", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tc", "1", "Turks and Caicos Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("td", "235", "Chad", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tg", "228", "Togo", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("th", "66", "Thailand", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tj", "992", "Tajikistan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tk", "690", "Tokelau", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tl", "670", "Timor-leste", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tm", "993", "Turkmenistan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tn", "216", "Tunisia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("to", "676", "Tonga", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tr", "90", "Turkey", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tt", "1", "Trinidad &amp; Tobago", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tv", "688", "Tuvalu", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tw", "886", "Taiwan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("tz", "255", "Tanzania, United Republic Of", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ua", "380", "Ukraine", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ug", "256", "Uganda", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("us", "1", "United States", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("uy", "598", "Uruguay", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("uz", "998", "Uzbekistan", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("va", "379", "Holy See (vatican City State)", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("vc", "1", "Saint Vincent &amp; The Grenadines", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ve", "58", "Venezuela, Bolivarian Republic Of", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("vg", "1", "British Virgin Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("vi", "1", "US Virgin Islands", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("vn", "84", "Vietnam", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("vu", "678", "Vanuatu", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("wf", "681", "Wallis And Futuna", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ws", "685", "Samoa", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("xk", "383", "Kosovo", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("ye", "967", "Yemen", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("yt", "262", "Mayotte", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("za", "27", "South Africa", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("zm", "260", "Zambia", DEFAULT_FLAG_RES))
                countries.add(CCPCountry("zw", "263", "Zimbabwe", DEFAULT_FLAG_RES))
                return countries
            }

        fun setEnglishName(ccpCountry: CCPCountry, ename: String) {
            ccpCountry.englishName = ename
        }

        fun getEnglishName(ccpCountry: CCPCountry): String {
            if (null == ccpCountry.englishName) {
                return " "
            }
            return ccpCountry.englishName!!
        }

    }

    public fun getCountryForCode(
        context: Context,
        language: CountryCodePicker.Language,
        preferredCountries: List<CCPCountry>,
        code: Int
    ): CCPCountry? {
        return getCountryForCode(context, language, preferredCountries, code.toString() + "")
    }

}
