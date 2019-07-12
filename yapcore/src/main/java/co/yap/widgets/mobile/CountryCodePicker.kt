package co.yap.widgets.mobile

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import co.yap.widgets.mobile.CCPCountry
import co.yap.widgets.mobile.CountryCodeDialog
import co.yap.widgets.mobile.InternationalPhoneTextWatcher
import co.yap.yapcore.R


import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.Locale

import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber


class CountryCodePicker : RelativeLayout {
    private var CCP_PREF_FILE = "CCP_PREF_FILE"
    private var defaultCountryCode: Int = 0
    private var defaultCountryNameCode: String? = null
    var mContext: Context=getContext()
    lateinit var holderView: View
    lateinit var mInflater: LayoutInflater
    lateinit var textView_selectedCountry: TextView
    private lateinit var editText_registeredCarrierNumber: EditText
    lateinit var holder: RelativeLayout
        private set
    lateinit var imageViewArrow: ImageView
    lateinit var imageViewFlag: ImageView
    lateinit var linearFlagBorder: LinearLayout
    lateinit var linearFlagHolder: LinearLayout
    lateinit var selectedCCPCountry: CCPCountry
    private//        Log.d(TAG, "Setting default country:" + defaultCountry.logString());
    lateinit var defaultCountry: CCPCountry
    lateinit var relativeClickConsumer: RelativeLayout
    lateinit var codePicker: CountryCodePicker
    private lateinit var currentTextGravity: TextGravity
    // see attr.xml to see corresponding values for pref
    var selectedAutoDetectionPref = AutoDetectionPref.SIM_NETWORK_LOCALE
    private lateinit var phoneUtil: PhoneNumberUtil
    var showNameCode = true
    private var showPhoneCode = true
    /**
     * To show/hide phone code from country selection dialog
     *
     * @param ccpDialogShowPhoneCode
     */
    var isCcpDialogShowPhoneCode = true
        set
    private var showFlag = true
    private var showFullName = false
    /**
     * Set visibility of fast scroller.
     *
     * @param showFastScroller
     */
    private var isShowFastScroller = true
        set
    /**
     * To show/hide name code from country selection dialog
     */
    /**
     * To show/hide title from country selection dialog
     *
     * @param ccpDialogShowTitle
     */
    var ccpDialogShowTitle = true
    /**
     * To show/hide flag from country selection dialog
     */
    /**
     * To show/hide flag from country selection dialog
     *
     * @param ccpDialogShowFlag
     */
    var ccpDialogShowFlag = true
    /**
     * SelectionDialogSearch is the facility to search through the list of country while selecting.
     *
     * @return true if search is set allowed
     */
    /**
     * SelectionDialogSearch is the facility to search through the list of country while selecting.
     *
     * @param searchAllowed true will allow search and false will hide search box
     */
    var isSearchAllowed = true
    private var showArrow = true
    protected var isShowCloseIcon = false
        private set
    private var rememberLastSelection = false
    private var detectCountryWithAreaCode = true
    /**
     * To show/hide name code from country selection dialog
     */
    /**
     * To show/hide name code from country selection dialog
     *
     * @param ccpDialogShowNameCode
     */
    var ccpDialogShowNameCode = true
    var isDialogInitialScrollToSelectionEnabled = false
        private set
    private var ccpUseEmoji = false
    private var ccpUseDummyEmojiForPreview = false
    private var isInternationalFormattingOnlyEnabled = true
    //        private set
    private var hintExampleNumberType = PhoneNumberType.MOBILE
    private var selectionMemoryTag: String? = "ccp_last_selection"
    private var contentColor = DEFAULT_UNSET
    private var arrowColor = DEFAULT_UNSET
    private var borderFlagColor: Int = 0
    private var dialogTypeFace: Typeface? = null
    var dialogTypeFaceStyle: Int = 0
    var preferredCountries: List<CCPCountry>? = null
    private var ccpTextgGravity = TEXT_GRAVITY_CENTER
    //this will be "AU,IN,US"
    private var countryPreference: String? = null
    /**
     * Sets bubble color for fast scroller
     *
     * @param fastScrollerBubbleColor
     */
    private var fastScrollerBubbleColor = 0
        set
    /**
     * @param customMasterCountriesList is list of countries that we need as custom master list
     */
    var customMasterCountriesList: List<CCPCountry>? = null
    //this will be "AU,IN,US"
    /**
     * @return comma separated custom master countries' name code. i.e "gb,us,nz,in,pk"
     */
    private var customMasterCountriesParam: String? = null
    private var excludedCountriesParam: String? = null
    private var customDefaultLanguage: Language? = Language.ENGLISH
    private var languageToApply: Language? = Language.ENGLISH

    /**
     * By default, keyboard pops up every time ccp is clicked and selection dialog is opened.
     *
     * @param dialogKeyboardAutoPopup true: to open keyboard automatically when selection dialog is opened
     * false: to avoid auto pop of keyboard
     */
    private var isDialogKeyboardAutoPopup = true
        set
    private var ccpClickable = true
    private var isAutoDetectLanguageEnabled = false
    private var isAutoDetectCountryEnabled = false
    private var numberAutoFormattingEnabled = true
    private var hintExampleNumberEnabled = false
    private var xmlWidth: String? = "notSet"
    lateinit var validityTextWatcher: TextWatcher
    private var formattingTextWatcher: InternationalPhoneTextWatcher? = null
    private var reportedValidity: Boolean = false
    private var areaCodeCountryDetectorTextWatcher: TextWatcher? = null
    private var countryDetectionBasedOnAreaAllowed: Boolean = false
    private var lastCheckedAreaCode: String? = null
    private var lastCursorPosition = 0
    private var countryChangedDueToAreaCode = false
    private var onCountryChangeListener: OnCountryChangeListener? = null
    private var phoneNumberValidityChangeListener: PhoneNumberValidityChangeListener? = null
    private var failureListener: FailureListener? = null
    /**
     * @return registered dialog event listener
     */
    /**
     * Dialog events listener will give call backs on various dialog events
     *
     * @param dialogEventsListener
     */
    protected var dialogEventsListener: DialogEventsListener? = null
        set
    private var customDialogTextProvider: CustomDialogTextProvider? = null
    /**
     * This should be the color for fast scroller handle.
     *
     * @param fastScrollerHandleColor
     */
    private var fastScrollerHandleColor = 0
        set
    /**
     * This will be color of dialog background
     *
     * @param dialogBackgroundColor
     */
    private var dialogBackgroundColor: Int = 0
        set
    /**
     * This color will be applied to
     * Title of dialog
     * Name of country
     * Phone code of country
     * "X" button to clear query
     * preferred country divider if preferred countries defined (semi transparent)
     *
     * @param dialogTextColor
     */
    var dialogTextColor: Int = 0
        set
    /**
     * If device is running above or equal LOLLIPOP version, this will change tint of search edittext background.
     *
     * @param dialogSearchEditTextTintColor
     */
    private var dialogSearchEditTextTintColor: Int = 0
        set
    /**
     * This sets text appearance for fast scroller index character
     *
     * @param fastScrollerBubbleTextAppearance should be reference id of textappereance style. i.e. R.style.myBubbleTextAppearance
     */
    private var fastScrollerBubbleTextAppearance = 0
        set
    private var currentCountryGroup: CCPCountryGroup? = null
    private var customClickListener: View.OnClickListener? = null
    private var countryCodeHolderClickListener: View.OnClickListener = OnClickListener { v ->
        if (customClickListener == null) {
            if (isCcpClickable) {
                if (isDialogInitialScrollToSelectionEnabled) {
                    launchCountrySelectionDialog(selectedCountryNameCode)
                } else {
                    launchCountrySelectionDialog()
                }
            }
        } else {
            customClickListener!!.onClick(v)
        }
    }
        private set

    /**
     * This will set boolean for numberAutoFormattingEnabled and refresh formattingTextWatcher
     *
     * @param numberAutoFormattingEnabled
     */
    private var isNumberAutoFormattingEnabled: Boolean
        get() = numberAutoFormattingEnabled
        set(numberAutoFormattingEnabled) {
            this.numberAutoFormattingEnabled = numberAutoFormattingEnabled
            if (editText_registeredCarrierNumber != null) {
                updateFormattingTextWatcher()
            }
        }

    /**
     * To show/hide phone code from ccp view
     *
     * @param showPhoneCode
     */
    private var isShowPhoneCode: Boolean
        get() = showPhoneCode
        set(showPhoneCode) {
            this.showPhoneCode = showPhoneCode
            selectedCountry = selectedCCPCountry
        }

    private//        Log.d(TAG, "getCCPLanguageFromLocale: current locale language" + currentLocale.getLanguage());
    val ccpLanguageFromLocale: Language?
        get() {
            val currentLocale = mContext.resources.configuration.locale
            for (language in Language.values()) {
                if (language.code!!.equals(currentLocale.language, ignoreCase = true)) {

                    if (language.country == null || language.country!!.equals(currentLocale.country, ignoreCase = true))
                        return language

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (language.script == null || language.script!!.equals(
                                currentLocale.script,
                                ignoreCase = true
                            )
                        )
                            return language

                    }
                }
            }
            return null
        }

    private//force disable area code country detection
    //as soon as country is selected, textView should be updated
    // add flag if required
    //                android studio preview shows huge space if 0 width space is not added.
    //show chequered flag if dummy preview is expected.
    // add full name to if required
    // adds name code if required
    // hide phone code if required
    //avoid blank state of ccp
    //        Log.d(TAG, "Setting selected country:" + selectedCountry.logString());
    //notify to registered validity listener
    //once updates are done, this will release lock
    //if the country was auto detected based on area code, this will correct the cursor position.
    //update country group
    var selectedCountry: CCPCountry?
        get() {
            if (selectedCCPCountry == null) {
                selectedCountry = defaultCountry
            }
            return selectedCCPCountry
        }
        private set(selectedCCPCountry) {
            var selectedCCPCountry = selectedCCPCountry
            countryDetectionBasedOnAreaAllowed = false
            lastCheckedAreaCode = ""
            if (selectedCCPCountry == null) {
                selectedCCPCountry = CCPCountry.getCountryForCode(
                    getContext(),
                    this!!.languageToApply!!,
                    this!!.preferredCountries!!,
                    defaultCountryCode
                )
                if (selectedCCPCountry == null) {
                    return
                }
            }

            this.selectedCCPCountry = selectedCCPCountry

            var displayText = ""
            if (showFlag && ccpUseEmoji) {
                if (isInEditMode) {
                    if (ccpUseDummyEmojiForPreview) {
                        displayText += "\uD83C\uDFC1\u200B "
                    } else {
                        displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "\u200B "
                    }

                } else {
                    displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "  "
                }
            }
            if (showFullName) {
                displayText = displayText + selectedCCPCountry.name
            }
            if (showNameCode) {
                if (showFullName) {
                    displayText += " (" + selectedCCPCountry.nameCode.toUpperCase() + ")"
                } else {
                    displayText += " " + selectedCCPCountry.nameCode.toUpperCase()
                }
            }
            if (showPhoneCode) {
                if (displayText.length > 0) {
                    displayText += "  "
                }
                displayText += "+" + selectedCCPCountry.phoneCode
            }

            textView_selectedCountry.text = displayText
            if (showFlag == false && displayText.length == 0) {
                displayText += "+" + selectedCCPCountry.phoneCode
                textView_selectedCountry.text = displayText
            }

            if (onCountryChangeListener != null) {
                onCountryChangeListener!!.onCountrySelected()
            }

            imageViewFlag.setImageResource(selectedCCPCountry.flagID)

            updateFormattingTextWatcher()

            updateHint()
            if (editText_registeredCarrierNumber != null && phoneNumberValidityChangeListener != null) {
                reportedValidity = isValidFullNumber
                phoneNumberValidityChangeListener!!.onValidityChanged(reportedValidity)
            }
            countryDetectionBasedOnAreaAllowed = true
            if (countryChangedDueToAreaCode) {
                try {
                    editText_registeredCarrierNumber!!.setSelection(lastCursorPosition)
                    countryChangedDueToAreaCode = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            updateCountryGroup()
        }

    /**
     * this function maps CountryCodePicker.PhoneNumberType to PhoneNumberUtil.PhoneNumberType.
     *
     * @return respective PhoneNumberUtil.PhoneNumberType based on selected CountryCodePicker.PhoneNumberType.
     */
    private val selectedHintNumberType: PhoneNumberUtil.PhoneNumberType
        get() {
            when (hintExampleNumberType) {
                CountryCodePicker.PhoneNumberType.MOBILE -> return PhoneNumberUtil.PhoneNumberType.MOBILE
                CountryCodePicker.PhoneNumberType.FIXED_LINE -> return PhoneNumberUtil.PhoneNumberType.FIXED_LINE
                CountryCodePicker.PhoneNumberType.FIXED_LINE_OR_MOBILE -> return PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE
                CountryCodePicker.PhoneNumberType.TOLL_FREE -> return PhoneNumberUtil.PhoneNumberType.TOLL_FREE
                CountryCodePicker.PhoneNumberType.PREMIUM_RATE -> return PhoneNumberUtil.PhoneNumberType.PREMIUM_RATE
                CountryCodePicker.PhoneNumberType.SHARED_COST -> return PhoneNumberUtil.PhoneNumberType.SHARED_COST
                CountryCodePicker.PhoneNumberType.VOIP -> return PhoneNumberUtil.PhoneNumberType.VOIP
                CountryCodePicker.PhoneNumberType.PERSONAL_NUMBER -> return PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER
                CountryCodePicker.PhoneNumberType.PAGER -> return PhoneNumberUtil.PhoneNumberType.PAGER
                CountryCodePicker.PhoneNumberType.UAN -> return PhoneNumberUtil.PhoneNumberType.UAN
                CountryCodePicker.PhoneNumberType.VOICEMAIL -> return PhoneNumberUtil.PhoneNumberType.VOICEMAIL
                CountryCodePicker.PhoneNumberType.UNKNOWN ->

                    return PhoneNumberUtil.PhoneNumberType.UNKNOWN
                else -> return PhoneNumberUtil.PhoneNumberType.MOBILE
            }
        }

    /**
     * This updates country dynamically as user types in area code
     *
     * @return
     */
    private//possible countries
    val countryDetectorTextWatcher: TextWatcher
        get() {

            if (editText_registeredCarrierNumber != null) {
                if (areaCodeCountryDetectorTextWatcher == null) {
                    areaCodeCountryDetectorTextWatcher = object : TextWatcher {
                        private var lastCheckedNumber: String? = null


                        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                        }

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                            var selectedCountry = selectedCountry
                            if (selectedCountry != null && (lastCheckedNumber == null || lastCheckedNumber != s.toString()) && countryDetectionBasedOnAreaAllowed) {
                                if (currentCountryGroup != null) {
                                    val enteredValue = getEditText_registeredCarrierNumber()!!.text.toString()
                                    if (enteredValue.length >= currentCountryGroup!!.areaCodeLength) {
                                        val digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue)
                                        if (digitsValue.length >= currentCountryGroup!!.areaCodeLength) {
                                            val currentAreaCode =
                                                digitsValue.substring(0, currentCountryGroup!!.areaCodeLength)
                                            if (currentAreaCode != lastCheckedAreaCode) {
                                                val detectedCountry = getLanguageToApply()?.let {
                                                    currentCountryGroup!!.getCountryForAreaCode(
                                                        mContext,
                                                        it,
                                                        currentAreaCode
                                                    )
                                                }
                                                if (detectedCountry != selectedCountry) {
                                                    countryChangedDueToAreaCode = true
                                                    lastCursorPosition = Selection.getSelectionEnd(s)
                                                    selectedCountry = detectedCountry
                                                }
                                                lastCheckedAreaCode = currentAreaCode
                                            }
                                        }
                                    }
                                }
                                lastCheckedNumber = s.toString()
                            }
                        }

                        override fun afterTextChanged(s: Editable) {


                        }
                    }
                }
            }
            return this!!.areaCodeCountryDetectorTextWatcher!!
        }


    /**
     * @return true if ccp is enabled for click
     */
    /**
     * Allow click and open dialog
     *
     * @param ccpClickable
     */
    private var isCcpClickable: Boolean
        get() = ccpClickable
        set(ccpClickable) {
            this.ccpClickable = ccpClickable
            if (!ccpClickable) {
                relativeClickConsumer.setOnClickListener(null)
                relativeClickConsumer.isClickable = false
                relativeClickConsumer.isEnabled = false
            } else {
                relativeClickConsumer.setOnClickListener(countryCodeHolderClickListener)
                relativeClickConsumer.isClickable = true
                relativeClickConsumer.isEnabled = true
            }
        }

    /**
     * @return If custom text provider is registered, it will return value from provider else default.
     */
    private val dialogTitle: String
        get() {
            val defaultTitle = CCPCountry.getDialogTitle(mContext, getLanguageToApply()!!)
            return if (customDialogTextProvider != null) {
                customDialogTextProvider!!.getCCPDialogTitle(getLanguageToApply()!!, defaultTitle)
            } else {
                defaultTitle
            }
        }

    /**
     * @return If custom text provider is registered, it will return value from provider else default.
     */
    private val searchHintText: String
        get() {
            val defaultHint = CCPCountry.getSearchHintMessage(mContext, getLanguageToApply()!!)
            return if (customDialogTextProvider != null) {
                customDialogTextProvider!!.getCCPDialogSearchHintText( getLanguageToApply()!!, defaultHint)
            } else {
                defaultHint
            }
        }

    /**
     * @return If custom text provider is registered, it will return value from provider else default.
     */
    private val noResultACK: String
        get() {
            val defaultNoResultACK = CCPCountry.getNoResultFoundAckMessage(mContext, getLanguageToApply()!!)
            return if (customDialogTextProvider != null) {
                customDialogTextProvider!!.getCCPDialogNoResultACK( getLanguageToApply()!!, defaultNoResultACK)
            } else {
                defaultNoResultACK
            }
        }

    /**
     * * To get code of default country as Integer.
     *
     * @return integer value of default country code in CCP
     * i.e if default country is IN +91(India)  returns: 91
     * if default country is JP +81(Japan) returns: 81
     */
    val defaultCountryCodeAsInt: Int
        get() {
            var code = 0
            try {
                code = Integer.parseInt(getDefaultCountryCode())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return code
        }

    /**
     * To get code of default country with prefix "+".
     *
     * @return String value of default country code in CCP with prefix "+"
     * i.e if default country is IN +91(India)  returns: "+91"
     * if default country is JP +81(Japan) returns: "+81"
     */
    val defaultCountryCodeWithPlus: String
        get() = "+" + getDefaultCountryCode()

    /**
     * To get name of default country.
     *
     * @return String value of country name, default in CCP
     * i.e if default country is IN +91(India)  returns: "India"
     * if default country is JP +81(Japan) returns: "Japan"
     */
    val defaultCountryName: String
        get() = defaultCountry!!.name

    /**
     * To get code of selected country.
     *
     * @return String value of selected country code in CCP
     * i.e if selected country is IN +91(India)  returns: "91"
     * if selected country is JP +81(Japan) returns: "81"
     */
    val selectedCountryCode: String
        get() = selectedCountry!!.phoneCode

    /**
     * To get code of selected country with prefix "+".
     *
     * @return String value of selected country code in CCP with prefix "+"
     * i.e if selected country is IN +91(India)  returns: "+91"
     * if selected country is JP +81(Japan) returns: "+81"
     */
    val selectedCountryCodeWithPlus: String
        get() = "+$selectedCountryCode"

    /**
     * * To get code of selected country as Integer.
     *
     * @return integer value of selected country code in CCP
     * i.e if selected country is IN +91(India)  returns: 91
     * if selected country is JP +81(Japan) returns: 81
     */
    val selectedCountryCodeAsInt: Int
        get() {
            var code = 0
            try {
                code = Integer.parseInt(selectedCountryCode)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return code
        }

    /**
     * To get name of selected country.
     *
     * @return String value of country name, selected in CCP
     * i.e if selected country is IN +91(India)  returns: "India"
     * if selected country is JP +81(Japan) returns: "Japan"
     */
    val selectedCountryName: String
        get() = selectedCountry!!.name

    /**
     * To get name of selected country in English.
     *
     * @return String value of country name in English language, selected in CCP
     * i.e if selected country is IN +91(India)  returns: "India" no matter what language is currently selected.
     * if selected country is JP +81(Japan) returns: "Japan"
     */
    val selectedCountryEnglishName: String
        get() = selectedCountry!!.englishName

    /**
     * To get name code of selected country.
     *
     * @return String value of country name, selected in CCP
     * i.e if selected country is IN +91(India)  returns: "IN"
     * if selected country is JP +81(Japan) returns: "JP"
     */
    val selectedCountryNameCode: String
        get() = selectedCountry!!.nameCode.toUpperCase()

    private val enteredPhoneNumber: Phonenumber.PhoneNumber
        @Throws(NumberParseException::class)
        get() {
            var carrierNumber = ""
            if (editText_registeredCarrierNumber != null) {
                carrierNumber = PhoneNumberUtil.normalizeDigitsOnly(editText_registeredCarrierNumber!!.text.toString())
            }
            return getPhoneUtil()!!.parse(carrierNumber, selectedCountryNameCode)
        }

    /**
     * This function combines selected country code from CCP and carrier number from @param editTextCarrierNumber
     *
     * @return Full number is countryCode + carrierNumber i.e countryCode= 91 and carrier number= 8866667722, this will return "918866667722"
     */
    /**
     * Separate out country code and carrier number from fullNumber.
     * Sets country of separated country code in CCP and carrier number as text of editTextCarrierNumber
     * If no valid country code is found from full number, CCP will be set to default country code and full number will be set as carrier number to editTextCarrierNumber.
     *
     * @param fullNumber is combination of country code and carrier number, (country_code+carrier_number) for example if country is India (+91) and carrier/mobile number is 8866667722 then full number will be 9188666667722 or +918866667722. "+" in starting of number is optional.
     */
    var fullNumber: String
        get() {
            try {
                val phoneNumber = enteredPhoneNumber
                return getPhoneUtil()!!.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164).substring(1)
            } catch (e: NumberParseException) {
                Log.e(TAG, "getFullNumber: Could not parse number")
                return selectedCountryCode
            }

        }
        set(fullNumber) {
            var country: CCPCountry? =
                CCPCountry.getCountryForNumber(getContext(),  getLanguageToApply()!!, preferredCountries, fullNumber)
            if (country == null)
                country = defaultCountry
            selectedCountry = country
            val carrierNumber = detectCarrierNumber(fullNumber, country)
            if (getEditText_registeredCarrierNumber() != null) {
                getEditText_registeredCarrierNumber()!!.setText(carrierNumber)
                updateFormattingTextWatcher()
            } else {
                Log.w(
                    TAG,
                    "EditText for carrier number is not registered. Register it using registerCarrierNumberEditText() before getFullNumber() or setFullNumber()."
                )
            }
        }

    /**
     * This function combines selected country code from CCP and carrier number from @param editTextCarrierNumber
     * This will return formatted number.
     *
     * @return Full number is countryCode + carrierNumber i.e countryCode= 91 and carrier number= 8866667722, this will return "918866667722"
     */
    val formattedFullNumber: String
        get() {
            try {
                val phoneNumber = enteredPhoneNumber
                return "+" + getPhoneUtil()!!.format(
                    phoneNumber,
                    PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
                ).substring(1)
            } catch (e: NumberParseException) {
                Log.e(TAG, "getFullNumber: Could not parse number")
                return selectedCountryCode
            }

        }

    /**
     * This function combines selected country code from CCP and carrier number from @param editTextCarrierNumber and prefix "+"
     *
     * @return Full number is countryCode + carrierNumber i.e countryCode= 91 and carrier number= 8866667722, this will return "+918866667722"
     */
    val fullNumberWithPlus: String
        get() {
            try {
                val phoneNumber = enteredPhoneNumber
                return getPhoneUtil()!!.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
            } catch (e: NumberParseException) {
                Log.e(TAG, "getFullNumber: Could not parse number")
                return selectedCountryCode
            }

        }

    /**
     * This function will check the validity of entered number.
     * It will use PhoneNumberUtil to check validity
     *
     * @return true if entered carrier number is valid else false
     */
    //            when number could not be parsed, its not valid
    val isValidFullNumber: Boolean
        get() {
            try {
                if (getEditText_registeredCarrierNumber() != null && getEditText_registeredCarrierNumber()!!.text.length != 0) {
                    val phoneNumber = getPhoneUtil()!!.parse(
                        "+" + selectedCCPCountry!!.phoneCode + getEditText_registeredCarrierNumber()!!.text.toString(),
                        selectedCCPCountry!!.nameCode
                    )
                    return getPhoneUtil()!!.isValidNumber(phoneNumber)
                } else if (getEditText_registeredCarrierNumber() == null) {
                    Toast.makeText(mContext, "No editText for Carrier number found.", Toast.LENGTH_SHORT).show()
                    return false
                } else {
                    return false
                }
            } catch (e: NumberParseException) {
                return false
            }

        }

    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
        init(null)
    }

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs) {
        this.mContext = mContext
        init(attrs)
    }

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr) {
        this.mContext = mContext
        init(attrs)
    }

    /**
     * This will set boolean for internationalFormattingOnly and refresh formattingTextWatcher
     *
     * @param internationalFormattingOnly
     */
    fun setInternationalFormattingOnly(internationalFormattingOnly: Boolean) {
        this.isInternationalFormattingOnlyEnabled = internationalFormattingOnly
        if (editText_registeredCarrierNumber != null) {
            updateFormattingTextWatcher()
        }
    }

    private fun init(attrs: AttributeSet?) {
        mInflater = LayoutInflater.from(mContext)

        if (attrs != null) {
            xmlWidth = attrs.getAttributeValue(ANDROID_NAME_SPACE, "layout_width")
        }
        removeAllViewsInLayout()
        //at run time, match parent value returns LayoutParams.MATCH_PARENT ("-1"), for some android xml preview it returns "fill_parent"
        if (attrs != null && xmlWidth != null && (xmlWidth == RelativeLayout.LayoutParams.MATCH_PARENT.toString() + "" || xmlWidth == RelativeLayout.LayoutParams.FILL_PARENT.toString() + "" || xmlWidth == "fill_parent" || xmlWidth == "match_parent")) {
            holderView = mInflater.inflate(R.layout.layout_full_width_code_picker, this, true)
        } else {
            holderView = mInflater.inflate(R.layout.layout_code_picker, this, true)
        }

        textView_selectedCountry = holderView.findViewById<View>(R.id.textView_selectedCountry) as TextView
        holder = holderView.findViewById<View>(R.id.countryCodeHolder) as RelativeLayout
        imageViewArrow = holderView.findViewById<View>(R.id.imageView_arrow) as ImageView
        imageViewFlag = holderView.findViewById<View>(R.id.image_flag) as ImageView
        linearFlagHolder = holderView.findViewById<View>(R.id.linear_flag_holder) as LinearLayout
        linearFlagBorder = holderView.findViewById<View>(R.id.linear_flag_border) as LinearLayout
        relativeClickConsumer = holderView.findViewById<View>(R.id.rlClickConsumer) as RelativeLayout
        codePicker = this
        if (attrs != null) {
            applyCustomProperty(attrs)
        }
        relativeClickConsumer.setOnClickListener(countryCodeHolderClickListener)
    }

    private fun applyCustomProperty(attrs: AttributeSet) {
        //        Log.d(TAG, "Applying custom property");
        val a = mContext.theme.obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0)
        //default country code
        try {
            //hide nameCode. If someone wants only phone code to avoid name collision for same country phone code.
            showNameCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_showNameCode, true)

            //number auto formatting
            numberAutoFormattingEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoFormatNumber, true)

            //show phone code.
            showPhoneCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_showPhoneCode, true)

            //show phone code on dialog
            isCcpDialogShowPhoneCode =
                a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showPhoneCode, showPhoneCode)

            //show name code on dialog
            ccpDialogShowNameCode = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showNameCode, true)

            //show title on dialog
            ccpDialogShowTitle = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showTitle, true)

            //show title on dialog
            ccpUseEmoji = a.getBoolean(R.styleable.CountryCodePicker_ccp_useFlagEmoji, false)

            //show title on dialog
            ccpUseDummyEmojiForPreview = a.getBoolean(R.styleable.CountryCodePicker_ccp_useDummyEmojiForPreview, false)

            //show flag on dialog
            ccpDialogShowFlag = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showFlag, true)

            //ccpDialog initial scroll to selection
            isDialogInitialScrollToSelectionEnabled =
                a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_initialScrollToSelection, false)

            //show full name
            showFullName = a.getBoolean(R.styleable.CountryCodePicker_ccp_showFullName, false)

            //show fast scroller
            isShowFastScroller = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showFastScroller, true)

            //bubble color
            fastScrollerBubbleColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_fastScroller_bubbleColor, 0)

            //scroller handle color
            fastScrollerHandleColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_fastScroller_handleColor, 0)

            //scroller text appearance
            fastScrollerBubbleTextAppearance =
                a.getResourceId(R.styleable.CountryCodePicker_ccpDialog_fastScroller_bubbleTextAppearance, 0)

            //auto detect language
            isAutoDetectLanguageEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoDetectLanguage, false)

            //detect country from area code
            detectCountryWithAreaCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_areaCodeDetectedCountry, true)

            //remember last selection
            rememberLastSelection = a.getBoolean(R.styleable.CountryCodePicker_ccp_rememberLastSelection, false)

            //example number hint enabled?
            hintExampleNumberEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_hintExampleNumber, false)

            //international formatting only
            isInternationalFormattingOnlyEnabled =
                a.getBoolean(R.styleable.CountryCodePicker_ccp_internationalFormattingOnly, true)

            //example number hint type
            val hintNumberTypeIndex = a.getInt(R.styleable.CountryCodePicker_ccp_hintExampleNumberType, 0)
            hintExampleNumberType = PhoneNumberType.values()[hintNumberTypeIndex]

            //memory tag name for selection
            selectionMemoryTag = a.getString(R.styleable.CountryCodePicker_ccp_selectionMemoryTag)
            if (selectionMemoryTag == null) {
                selectionMemoryTag = "CCP_last_selection"
            }

            //country auto detection pref
            val autoDetectionPrefValue = a.getInt(R.styleable.CountryCodePicker_ccp_countryAutoDetectionPref, 123)
            selectedAutoDetectionPref = AutoDetectionPref.getPrefForValue(autoDetectionPrefValue.toString())

            //auto detect county
            isAutoDetectCountryEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoDetectCountry, false)

            //show arrow
            showArrow = a.getBoolean(R.styleable.CountryCodePicker_ccp_showArrow, true)
            refreshArrowViewVisibility()

            //show close icon
            isShowCloseIcon = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showCloseIcon, false)

            //show flag
            showFlag(a.getBoolean(R.styleable.CountryCodePicker_ccp_showFlag, true))

            //autopop keyboard
            isDialogKeyboardAutoPopup = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_keyboardAutoPopup, true)

            //if custom default language is specified, then set it as custom else sets english as custom
            val attrLanguage: Int
            attrLanguage = a.getInt(R.styleable.CountryCodePicker_ccp_defaultLanguage, Language.ENGLISH.ordinal)
            customDefaultLanguage = getLanguageEnum(attrLanguage)
            updateLanguageToApply()

            //custom master list
            customMasterCountriesParam = a.getString(R.styleable.CountryCodePicker_ccp_customMasterCountries)
            excludedCountriesParam = a.getString(R.styleable.CountryCodePicker_ccp_excludedCountries)
            if (!isInEditMode) {
                refreshCustomMasterList()
            }

            //preference
            countryPreference = a.getString(R.styleable.CountryCodePicker_ccp_countryPreference)
            //as3 is raising problem while rendering preview. to avoid such issue, it will update preferred list only on run time.
            if (!isInEditMode) {
                refreshPreferredCountries()
            }

            //text gravity
            if (a.hasValue(R.styleable.CountryCodePicker_ccp_textGravity)) {
                ccpTextgGravity = a.getInt(R.styleable.CountryCodePicker_ccp_textGravity, TEXT_GRAVITY_CENTER)
            }
            applyTextGravity(ccpTextgGravity)

            //default country
            //AS 3 has some problem with reading list so this is to make CCP preview work
            defaultCountryNameCode = a.getString(R.styleable.CountryCodePicker_ccp_defaultNameCode)
            var setUsingNameCode = false
            if (defaultCountryNameCode != null && defaultCountryNameCode!!.length != 0) {
                if (!isInEditMode) {
                    if (CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                            getContext(),
                             getLanguageToApply()!!,
                            defaultCountryNameCode
                        ) != null
                    ) {
                        setUsingNameCode = true
                        defaultCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                            getContext(),
                             getLanguageToApply()!!,
                            defaultCountryNameCode
                        )
                        selectedCountry = defaultCountry
                    }
                } else {
                    if (CCPCountry.getCountryForNameCodeFromEnglishList(defaultCountryNameCode) != null) {
                        setUsingNameCode = true
                        defaultCountry = CCPCountry.getCountryForNameCodeFromEnglishList(defaultCountryNameCode)
                        selectedCountry = defaultCountry
                    }
                }

                //when it was not set means something was wrong with name code
                if (!setUsingNameCode) {
                    defaultCountry = CCPCountry.getCountryForNameCodeFromEnglishList("IN")
                    selectedCountry = defaultCountry
                    setUsingNameCode = true
                }
            }

            //if default country is not set using name code.
            var defaultCountryCode = a.getInteger(R.styleable.CountryCodePicker_ccp_defaultPhoneCode, -1)
            if (!setUsingNameCode && defaultCountryCode != -1) {
                if (!isInEditMode) {
                    //if invalid country is set using xml, it will be replaced with LIB_DEFAULT_COUNTRY_CODE
                    if (defaultCountryCode != -1 && CCPCountry.getCountryForCode(
                            getContext(),
                             getLanguageToApply()!!,
                            preferredCountries,
                            defaultCountryCode
                        ) == null
                    ) {
                        defaultCountryCode = LIB_DEFAULT_COUNTRY_CODE
                    }
                    setDefaultCountryUsingPhoneCode(defaultCountryCode)
                    selectedCountry = defaultCountry
                } else {
                    //when it is in edit mode, we will check in english list only.
                    var defaultCountry: CCPCountry? =
                        CCPCountry.getCountryForCodeFromEnglishList(defaultCountryCode.toString() + "")
                    if (defaultCountry == null) {
                        defaultCountry =
                            CCPCountry.getCountryForCodeFromEnglishList(LIB_DEFAULT_COUNTRY_CODE.toString() + "")
                    }
                    defaultCountry = defaultCountry
                    selectedCountry = defaultCountry
                }
            }

            //if default country is not set using nameCode or phone code, let's set library default as default
            if (defaultCountry == null) {
                defaultCountry = CCPCountry.getCountryForNameCodeFromEnglishList("IN")
                if (selectedCountry == null) {
                    selectedCountry = defaultCountry
                }
            }


            //set auto detected country
            if (isAutoDetectCountryEnabled && !isInEditMode) {
                setAutoDetectedCountry(true)
            }

            //set last selection
            if (rememberLastSelection && !isInEditMode) {
                loadLastSelectedCountryInCCP()
            }

            val arrowColor: Int
            arrowColor = a.getColor(R.styleable.CountryCodePicker_ccp_arrowColor, DEFAULT_UNSET)
            setArrowColor(arrowColor)

            //content color
            val contentColor: Int
            if (isInEditMode) {
                contentColor = a.getColor(R.styleable.CountryCodePicker_ccp_contentColor, DEFAULT_UNSET)
            } else {
                contentColor = a.getColor(
                    R.styleable.CountryCodePicker_ccp_contentColor,
                    mContext.resources.getColor(R.color.defaultContentColor)
                )
            }
            if (contentColor != DEFAULT_UNSET) {
                setContentColor(contentColor)
            }

            // flag border color
            val borderFlagColor: Int
            if (isInEditMode) {
                borderFlagColor = a.getColor(R.styleable.CountryCodePicker_ccp_flagBorderColor, 0)
            } else {
                borderFlagColor = a.getColor(
                    R.styleable.CountryCodePicker_ccp_flagBorderColor,
                    mContext.resources.getColor(R.color.defaultBorderFlagColor)
                )
            }
            if (borderFlagColor != 0) {
                setFlagBorderColor(borderFlagColor)
            }

            //dialog colors
            dialogBackgroundColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_backgroundColor, 0)
            dialogTextColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_textColor, 0)
            dialogSearchEditTextTintColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_searchEditTextTint, 0)

            //text size
            val textSize = a.getDimensionPixelSize(R.styleable.CountryCodePicker_ccp_textSize, 0)
            if (textSize > 0) {
                textView_selectedCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
                setFlagSize(textSize)
                setArrowSize(textSize)
            }

            //if arrow size is explicitly defined
            val arrowSize = a.getDimensionPixelSize(R.styleable.CountryCodePicker_ccp_arrowSize, 0)
            if (arrowSize > 0) {
                setArrowSize(arrowSize)
            }

            isSearchAllowed = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_allowSearch, true)
            isCcpClickable = a.getBoolean(R.styleable.CountryCodePicker_ccp_clickable, true)

        } catch (e: Exception) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            textView_selectedCountry.maxLines = 25
            textView_selectedCountry.textSize = 10f
            textView_selectedCountry.text = sw.toString()
        } finally {
            a.recycle()
        }
    }

    private fun refreshArrowViewVisibility() {
        if (showArrow) {
            imageViewArrow.visibility = View.VISIBLE
        } else {
            imageViewArrow.visibility = View.GONE
        }
    }

    /**
     * this will read last selected country name code from the shared pref.
     * if that name code is not null, load that country in the CCP
     * else leaves as it is.(when used for the first time)
     */
    private fun loadLastSelectedCountryInCCP() {
        //get the shared pref
        val sharedPref = mContext.getSharedPreferences(
            CCP_PREF_FILE, Context.MODE_PRIVATE
        )

        // read last selection value
        val lastSelectedCountryNameCode = sharedPref.getString(selectionMemoryTag, null)

        //if last selection value is not null, load it into the CCP
        if (lastSelectedCountryNameCode != null) {
            setCountryForNameCode(lastSelectedCountryNameCode)
        }
    }

    /**
     * This will store the selected name code in the preferences
     *
     * @param selectedCountryNameCode name code of the selected country
     */
    private fun storeSelectedCountryNameCode(selectedCountryNameCode: String) {
        //get the shared pref
        val sharedPref = mContext.getSharedPreferences(
            CCP_PREF_FILE, Context.MODE_PRIVATE
        )

        //we want to write in shared pref, so lets get editor for it
        val editor = sharedPref.edit()

        // add our last selection country name code in pref
        editor.putString(selectionMemoryTag, selectedCountryNameCode)

        //finally save it...
        editor.apply()
    }

    private fun getCurrentTextGravity(): TextGravity {
        return currentTextGravity
    }

    /**
     * When width is set "match_parent", this gravity will set placement of text (Between flag and down arrow).
     *
     * @param textGravity expected placement
     */
    fun setCurrentTextGravity(textGravity: TextGravity) {
        this.currentTextGravity = textGravity
        applyTextGravity(textGravity.enumIndex)
    }

    private fun applyTextGravity(enumIndex: Int) {
        if (enumIndex == TextGravity.LEFT.enumIndex) {
            textView_selectedCountry.gravity = Gravity.LEFT
        } else if (enumIndex == TextGravity.CENTER.enumIndex) {
            textView_selectedCountry.gravity = Gravity.CENTER
        } else {
            textView_selectedCountry.gravity = Gravity.RIGHT
        }
    }

    /**
     * which language to show is decided based on
     * autoDetectLanguage flag
     * if autoDetectLanguage is true, then it should check language based on locale, if no language is found based on locale, customDefault language will returned
     * else autoDetectLanguage is false, then customDefaultLanguage will be returned.
     *
     * @return
     */
    private fun updateLanguageToApply() {
        //when in edit mode, it will return default language only
        if (isInEditMode) {
            if (customDefaultLanguage != null) {
                languageToApply = customDefaultLanguage
            } else {
                languageToApply = Language.ENGLISH
            }
        } else {
            if (isAutoDetectLanguageEnabled) {
                val localeBasedLanguage = ccpLanguageFromLocale
                if (localeBasedLanguage == null) { //if no language is found from locale
                    if (getCustomDefaultLanguage() != null) { //and custom language is defined
                        languageToApply = getCustomDefaultLanguage()
                    } else {
                        languageToApply = Language.ENGLISH
                    }
                } else {
                    languageToApply = localeBasedLanguage
                }
            } else {
                if (getCustomDefaultLanguage() != null) {
                    languageToApply = customDefaultLanguage
                } else {
                    languageToApply = Language.ENGLISH  //library default
                }
            }
        }
    }

    /**
     * update country group
     */
    private fun updateCountryGroup() {
        currentCountryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(selectedCountryCodeAsInt)
    }

    /**
     * updates hint
     */
    private fun updateHint() {
        if (editText_registeredCarrierNumber != null && hintExampleNumberEnabled) {
            var formattedNumber = ""
            val exampleNumber =
                getPhoneUtil()!!.getExampleNumberForType(selectedCountryNameCode, selectedHintNumberType)
            if (exampleNumber != null) {
                formattedNumber = exampleNumber!!.getNationalNumber() + ""
                //                Log.d(TAG, "updateHint: " + formattedNumber);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    formattedNumber = PhoneNumberUtils.formatNumber(
                        selectedCountryCodeWithPlus + formattedNumber,
                        selectedCountryNameCode
                    )
                } else {
                    formattedNumber = PhoneNumberUtils.formatNumber(selectedCountryCodeWithPlus + formattedNumber)
                }
                formattedNumber = formattedNumber.substring(selectedCountryCodeWithPlus.length).trim { it <= ' ' }
                //                Log.d(TAG, "updateHint: after format " + formattedNumber + " " + selectionMemoryTag);
            } else {
                //                Log.w(TAG, "updateHint: No example number found for this country (" + getSelectedCountryNameCode() + ") or this type (" + hintExampleNumberType.name() + ").");
            }
            editText_registeredCarrierNumber!!.hint = formattedNumber
        }
    }

    fun getLanguageToApply(): Language? {
        if (languageToApply == null) {
            updateLanguageToApply()
        }
        return languageToApply
    }

    private fun setLanguageToApply(languageToApply: Language) {
        this.languageToApply = languageToApply
    }

    private fun updateFormattingTextWatcher() {
        if (editText_registeredCarrierNumber != null && selectedCCPCountry != null) {
            val enteredValue = getEditText_registeredCarrierNumber()!!.text.toString()
            val digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue)

            if (formattingTextWatcher != null) {
                editText_registeredCarrierNumber!!.removeTextChangedListener(formattingTextWatcher)
            }

            if (areaCodeCountryDetectorTextWatcher != null) {
                editText_registeredCarrierNumber!!.removeTextChangedListener(areaCodeCountryDetectorTextWatcher)
            }

            if (numberAutoFormattingEnabled) {
                formattingTextWatcher = InternationalPhoneTextWatcher(
                    mContext,
                    selectedCountryNameCode,
                    selectedCountryCodeAsInt,
                    isInternationalFormattingOnlyEnabled
                )
                editText_registeredCarrierNumber!!.addTextChangedListener(formattingTextWatcher)
            }

            //if country detection from area code is enabled, then it will add areaCodeCountryDetectorTextWatcher
            if (detectCountryWithAreaCode) {
                areaCodeCountryDetectorTextWatcher = countryDetectorTextWatcher
                editText_registeredCarrierNumber!!.addTextChangedListener(areaCodeCountryDetectorTextWatcher)
            }

            //text watcher stops working when it finds non digit character in previous phone code. This will reset its function
            editText_registeredCarrierNumber!!.setText("")
            editText_registeredCarrierNumber!!.setText(digitsValue)
            editText_registeredCarrierNumber!!.setSelection(editText_registeredCarrierNumber!!.text.length)
        } else {
            if (editText_registeredCarrierNumber == null) {
                Log.v(TAG, "updateFormattingTextWatcher: EditText not registered " + selectionMemoryTag!!)
            } else {
                Log.v(TAG, "updateFormattingTextWatcher: selected country is null " + selectionMemoryTag!!)
            }
        }
    }

    private fun getCustomDefaultLanguage(): Language? {
        return customDefaultLanguage
    }

    private fun setCustomDefaultLanguage(customDefaultLanguage: Language) {
        this.customDefaultLanguage = customDefaultLanguage
        updateLanguageToApply()
        selectedCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            mContext,
             getLanguageToApply()!!,
            selectedCCPCountry!!.nameCode
        )
    }

    /**
     * if true, this will give explicit close icon in CCP dialog
     *
     * @param showCloseIcon
     */
    fun showCloseIcon(showCloseIcon: Boolean) {
        this.isShowCloseIcon = showCloseIcon
    }

    private fun getEditText_registeredCarrierNumber(): EditText? {
        //        Log.d(TAG, "getEditText_registeredCarrierNumber");
        return editText_registeredCarrierNumber
    }

    /**
     * this will register editText and will apply required text watchers
     *
     * @param editText_registeredCarrierNumber
     */
    private fun setEditText_registeredCarrierNumber(editText_registeredCarrierNumber: EditText) {
        this.editText_registeredCarrierNumber = editText_registeredCarrierNumber
        //        Log.d(TAG, "setEditText_registeredCarrierNumber: carrierEditTextAttached " + selectionMemoryTag);
        updateValidityTextWatcher()
        updateFormattingTextWatcher()
        updateHint()
    }

    /**
     * This function will
     * - remove existing, if any, validityTextWatcher
     * - prepare new validityTextWatcher
     * - attach validityTextWatcher
     * - do initial reporting to watcher
     */
    private fun updateValidityTextWatcher() {
        try {
            editText_registeredCarrierNumber!!.removeTextChangedListener(validityTextWatcher)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //initial REPORTING
        reportedValidity = isValidFullNumber
        if (phoneNumberValidityChangeListener != null) {
            phoneNumberValidityChangeListener!!.onValidityChanged(reportedValidity)
        }

        validityTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (phoneNumberValidityChangeListener != null) {
                    val currentValidity: Boolean
                    currentValidity = isValidFullNumber
                    if (currentValidity != reportedValidity) {
                        reportedValidity = currentValidity
                        phoneNumberValidityChangeListener!!.onValidityChanged(reportedValidity)
                    }
                }
            }
        }

        editText_registeredCarrierNumber!!.addTextChangedListener(validityTextWatcher)
    }

    private fun getmInflater(): LayoutInflater {
        return mInflater
    }

    /**
     * Publicly available functions from library
     */

    fun getDialogTypeFace(): Typeface? {
        return dialogTypeFace
    }

    /**
     * To change font of ccp views
     *
     * @param typeFace
     */
    fun setDialogTypeFace(typeFace: Typeface) {
        try {
            dialogTypeFace = typeFace
            dialogTypeFaceStyle = DEFAULT_UNSET
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * this will load preferredCountries based on countryPreference
     */
    private fun refreshPreferredCountries() {
        if (countryPreference == null || countryPreference!!.length == 0) {
            preferredCountries = null
        } else {
            val localCCPCountryList = ArrayList<CCPCountry>()
            for (nameCode in countryPreference!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                val ccpCountry = CCPCountry.getCountryForNameCodeFromCustomMasterList(
                    getContext(),
                    customMasterCountriesList,
                     getLanguageToApply()!!,
                    nameCode
                )
                if (ccpCountry != null) {
                    if (!isAlreadyInList(ccpCountry, localCCPCountryList)) { //to avoid duplicate entry of country
                        localCCPCountryList.add(ccpCountry)
                    }
                }
            }

            if (localCCPCountryList.size == 0) {
                preferredCountries = null
            } else {
                preferredCountries = localCCPCountryList
            }
        }
        if (preferredCountries != null) {
            //            Log.d("preference list", preferredCountries.size() + " countries");
            for (CCPCountry in preferredCountries!!) {
                CCPCountry.log()
            }
        } else {
            //            Log.d("preference list", " has no country");
        }
    }

    /**
     * this will load preferredCountries based on countryPreference
     */
    fun refreshCustomMasterList() {
        //if no custom list specified then check for exclude list
        if (customMasterCountriesParam == null || customMasterCountriesParam!!.length == 0) {
            //if excluded param is also blank, then do nothing
            if (excludedCountriesParam != null && excludedCountriesParam!!.length != 0) {
                excludedCountriesParam = excludedCountriesParam!!.toLowerCase()
                val libraryMasterList = CCPCountry.getLibraryMasterCountryList(mContext, getLanguageToApply())
                val localCCPCountryList = ArrayList<CCPCountry>()
                for (ccpCountry in libraryMasterList) {
                    //if the country name code is in the excluded list, avoid it.
                    if (!excludedCountriesParam!!.contains(ccpCountry.nameCode.toLowerCase())) {
                        localCCPCountryList.add(ccpCountry)
                    }
                }

                if (localCCPCountryList.size > 0) {
                    customMasterCountriesList = localCCPCountryList
                } else {
                    customMasterCountriesList = null
                }

            } else {
                customMasterCountriesList = null
            }
        } else {
            //else add custom list
            val localCCPCountryList = ArrayList<CCPCountry>()
            for (nameCode in customMasterCountriesParam!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                val ccpCountry =
                    CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(),  getLanguageToApply()!!, nameCode)
                if (ccpCountry != null) {
                    if (!isAlreadyInList(ccpCountry, localCCPCountryList)) { //to avoid duplicate entry of country
                        localCCPCountryList.add(ccpCountry)
                    }
                }
            }

            if (localCCPCountryList.size == 0) {
                customMasterCountriesList = null
            } else {
                customMasterCountriesList = localCCPCountryList
            }
        }

        if (customMasterCountriesList != null) {
            //            Log.d("custom master list:", customMasterCountriesList.size() + " countries");
            for (ccpCountry in customMasterCountriesList!!) {
                ccpCountry.log()
            }
        } else {
            //            Log.d("custom master list", " has no country");
        }
    }

    /**
     * To provide definite set of countries when selection dialog is opened.
     * Only custom master countries, if defined, will be there is selection dialog to select from.
     * To set any country in preference, it must be included in custom master countries, if defined
     * When not defined or null or blank is set, it will use library's default master list
     * Custom master list will only limit the visibility of irrelevant country from selection dialog. But all other functions like setCountryForCodeName() or setFullNumber() will consider all the countries.
     *
     * @param customMasterCountriesParam is country name codes separated by comma. e.g. "us,in,nz"
     * if null or "" , will remove custom countries and library default will be used.
     */
    fun setCustomMasterCountries(customMasterCountriesParam: String) {
        this.customMasterCountriesParam = customMasterCountriesParam
    }

    /**
     * This can be used to remove certain countries from the list by keeping all the others.
     * This will be ignored if you have specified your own country master list.
     *
     * @param excludedCountries is country name codes separated by comma. e.g. "us,in,nz"
     * null or "" means no country is excluded.
     */
    fun setExcludedCountries(excludedCountries: String) {
        this.excludedCountriesParam = excludedCountries
        refreshCustomMasterList()
    }

    /**
     * This will match name code of all countries of list against the country's name code.
     *
     * @param CCPCountry
     * @param CCPCountryList list of countries against which country will be checked.
     * @return if country name code is found in list, returns true else return false
     */
    private fun isAlreadyInList(CCPCountry: CCPCountry?, CCPCountryList: List<CCPCountry>?): Boolean {
        if (CCPCountry != null && CCPCountryList != null) {
            for (iterationCCPCountry in CCPCountryList) {
                if (iterationCCPCountry.nameCode.equals(CCPCountry.nameCode, ignoreCase = true)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * This function removes possible country code from fullNumber and set rest of the number as carrier number.
     *
     * @param fullNumber combination of country code and carrier number.
     * @param CCPCountry selected country in CCP to detect country code part.
     */
    private fun detectCarrierNumber(fullNumber: String?, CCPCountry: CCPCountry?): String? {
        val carrierNumber: String?
        if (CCPCountry == null || fullNumber == null || fullNumber.isEmpty()) {
            carrierNumber = fullNumber
        } else {
            val indexOfCode = fullNumber.indexOf(CCPCountry.phoneCode)
            if (indexOfCode == -1) {
                carrierNumber = fullNumber
            } else {
                carrierNumber = fullNumber.substring(indexOfCode + CCPCountry.phoneCode.length)
            }
        }
        return carrierNumber
    }

    /**
     * Related to selected country
     */

    //add entry here
    private fun getLanguageEnum(index: Int): Language {
        return if (index < Language.values().size) {
            Language.values()[index]
        } else {
            Language.ENGLISH
        }
    }

    /**
     * This method is not encouraged because this might set some other country which have same country code as of yours. e.g 1 is common for US and canada.
     * If you are trying to set US ( and countryPreference is not set) and you pass 1 as @param defaultCountryCode, it will set canada (prior in list due to alphabetical order)
     * Rather use @function setDefaultCountryUsingNameCode("us"); or setDefaultCountryUsingNameCode("US");
     *
     *
     * Default country code defines your default country.
     * Whenever invalid / improper number is found in setCountryForPhoneCode() /  setFullNumber(), it CCP will set to default country.
     * This function will not set default country as selected in CCP. To set default country in CCP call resetToDefaultCountry() right after this call.
     * If invalid defaultCountryCode is applied, it won't be changed.
     *
     * @param defaultCountryCode code of your default country
     * if you want to set IN +91(India) as default country, defaultCountryCode =  91
     * if you want to set JP +81(Japan) as default country, defaultCountryCode =  81
     */
    @Deprecated("")
    fun setDefaultCountryUsingPhoneCode(defaultCountryCode: Int) {
        val defaultCCPCountry = CCPCountry.getCountryForCode(
            getContext(),
             getLanguageToApply()!!,
            preferredCountries,
            defaultCountryCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            //            Log.d(TAG, "No country for code " + defaultCountryCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryCode = defaultCountryCode
            defaultCountry = defaultCCPCountry
        }
    }

    /**
     * Default country name code defines your default country.
     * Whenever invalid / improper name code is found in setCountryForNameCode(), CCP will set to default country.
     * This function will not set default country as selected in CCP. To set default country in CCP call resetToDefaultCountry() right after this call.
     * If invalid defaultCountryCode is applied, it won't be changed.
     *
     * @param defaultCountryNameCode code of your default country
     * if you want to set IN +91(India) as default country, defaultCountryCode =  "IN" or "in"
     * if you want to set JP +81(Japan) as default country, defaultCountryCode =  "JP" or "jp"
     */
    fun setDefaultCountryUsingNameCode(defaultCountryNameCode: String) {
        val defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            getContext(),
             getLanguageToApply()!!,
            defaultCountryNameCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            //            Log.d(TAG, "No country for nameCode " + defaultCountryNameCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryNameCode = defaultCCPCountry.nameCode
            defaultCountry = defaultCCPCountry
        }
    }

    /**
     * @return: Country Code of default country
     * i.e if default country is IN +91(India)  returns: "91"
     * if default country is JP +81(Japan) returns: "81"
     */
    fun getDefaultCountryCode(): String {
        return defaultCountry!!.phoneCode
    }

    /**
     * To get name code of default country.
     *
     * @return String value of country name, default in CCP
     * i.e if default country is IN +91(India)  returns: "IN"
     * if default country is JP +81(Japan) returns: "JP"
     */
    fun getDefaultCountryNameCode(): String {
        return defaultCountry!!.nameCode.toUpperCase()
    }

    /**
     * reset the default country as selected country.
     */
    fun resetToDefaultCountry() {
        defaultCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            getContext(),
             getLanguageToApply()!!,
            getDefaultCountryNameCode()
        )
        selectedCountry = defaultCountry
    }

    /**
     * This will set country with @param countryCode as country code, in CCP
     *
     * @param countryCode a valid country code.
     * If you want to set IN +91(India), countryCode= 91
     * If you want to set JP +81(Japan), countryCode= 81
     */
    fun setCountryForPhoneCode(countryCode: Int) {
        val ccpCountry = CCPCountry.getCountryForCode(
            getContext(),
             getLanguageToApply()!!,
            preferredCountries,
            countryCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (ccpCountry == null) {
            if (defaultCountry == null) {
                defaultCountry = ccpCountry!!.getCountryForCode(
                    getContext(),
                     getLanguageToApply()!!,
                    preferredCountries,
                    defaultCountryCode
                )
            }
            selectedCountry = defaultCountry
        } else {
            selectedCountry = ccpCountry
        }
    }

    /**
     * This will set country with @param countryNameCode as country name code, in CCP
     *
     * @param countryNameCode a valid country name code.
     * If you want to set IN +91(India), countryCode= IN
     * If you want to set JP +81(Japan), countryCode= JP
     */
    fun setCountryForNameCode(countryNameCode: String) {
        val country = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            getContext(),
             getLanguageToApply()!!,
            countryNameCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (country == null) {
            if (defaultCountry == null) {
                defaultCountry = country!!.getCountryForCode(
                    getContext(),
                     getLanguageToApply()!!,
                    preferredCountries,
                    defaultCountryCode
                )
            }
            selectedCountry = defaultCountry
        } else {
            selectedCountry = country
        }
    }

    /**
     * All functions that work with fullNumber need an editText to write and read carrier number of full number.
     * An editText for carrier number must be registered in order to use functions like setFullNumber() and getFullNumber().
     *
     * @param editTextCarrierNumber - an editText where user types carrier number ( the part of full number other than country code).
     */
    fun registerCarrierNumberEditText(editTextCarrierNumber: EditText) {
        setEditText_registeredCarrierNumber(editTextCarrierNumber)
    }

    /**
     * If edittext was already registered, this will remove attached textwatchers and set
     * editText to null
     */
    fun deregisterCarrierNumberEditText() {
        if (editText_registeredCarrierNumber != null) {
            // remove validity listener
            try {
                editText_registeredCarrierNumber!!.removeTextChangedListener(validityTextWatcher)
            } catch (ignored: Exception) {

            }

            // if possible, remove formatting textwatcher
            try {
                editText_registeredCarrierNumber!!.removeTextChangedListener(formattingTextWatcher)
            } catch (ignored: Exception) {

            }

            editText_registeredCarrierNumber!!.hint = ""

            editText_registeredCarrierNumber = null
        }
    }

    /**
     * @return content color of CCP's text and small downward arrow.
     */
    fun getContentColor(): Int {
        return contentColor
    }

    /**
     * Sets text and small down arrow color of CCP.
     *
     * @param contentColor color to apply to text and down arrow
     */
    fun setContentColor(contentColor: Int) {
        this.contentColor = contentColor
        textView_selectedCountry.setTextColor(this.contentColor)

        //change arrow color only if explicit arrow color is not specified.
        if (this.arrowColor == DEFAULT_UNSET) {
            imageViewArrow.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN)
        }
    }

    /**
     * set small down arrow color of CCP.
     *
     * @param arrowColor color to apply to text and down arrow
     */
    fun setArrowColor(arrowColor: Int) {
        this.arrowColor = arrowColor
        if (this.arrowColor == DEFAULT_UNSET) {
            if (contentColor != DEFAULT_UNSET) {
                imageViewArrow.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN)
            }
        } else {
            imageViewArrow.setColorFilter(this.arrowColor, PorterDuff.Mode.SRC_IN)
        }
    }

    /**
     * Sets flag border color of CCP.
     *
     * @param borderFlagColor color to apply to flag border
     */
    fun setFlagBorderColor(borderFlagColor: Int) {
        this.borderFlagColor = borderFlagColor
        linearFlagBorder.setBackgroundColor(this.borderFlagColor)
    }

    /**
     * Modifies size of text in side CCP view.
     *
     * @param textSize size of text in pixels
     */
    fun setTextSize(textSize: Int) {
        if (textSize > 0) {
            textView_selectedCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            setArrowSize(textSize)
            setFlagSize(textSize)
        }
    }

    /**
     * Modifies size of downArrow in CCP view
     *
     * @param arrowSize size in pixels
     */
    fun setArrowSize(arrowSize: Int) {
        if (arrowSize > 0) {
            val params = imageViewArrow.layoutParams as RelativeLayout.LayoutParams
            params.width = arrowSize
            params.height = arrowSize
            imageViewArrow.layoutParams = params
        }
    }

    /**
     * If nameCode of country in CCP view is not required use this to show/hide country name code of ccp view.
     *
     * @param showNameCode true will show country name code in ccp view, it will result " (IN) +91 "
     * false will remove country name code from ccp view, it will result  " +91 "
     */
    fun showNameCode(showNameCode: Boolean) {
        this.showNameCode = showNameCode
        selectedCountry = selectedCCPCountry
    }

    /**
     * This can change visility of arrow.
     *
     * @param showArrow true will show arrow and false will hide arrow from there.
     */
    fun showArrow(showArrow: Boolean) {
        this.showArrow = showArrow
        refreshArrowViewVisibility()
    }

    /**
     * This will set preferred countries using their name code. Prior preferred countries will be replaced by these countries.
     * Preferred countries will be at top of country selection box.
     * If more than one countries have same country code, country in preferred list will have higher priory than others. e.g. Canada and US have +1 as their country code. If "us" is set as preferred country then US will be selected whenever setCountryForPhoneCode(1); or setFullNumber("+1xxxxxxxxx"); is called.
     *
     * @param countryPreference is country name codes separated by comma. e.g. "us,in,nz"
     */
    fun setCountryPreference(countryPreference: String) {
        this.countryPreference = countryPreference
    }

    /**
     * Language will be applied to country select dialog
     * If autoDetectCountry is true, ccp will try to detect language from locale.
     * Detected language is supported If no language is detected or detected language is not supported by ccp, it will set default language as set.
     *
     * @param language
     */
    fun changeDefaultLanguage(language: Language) {
        setCustomDefaultLanguage(language)
    }

    /**
     * To change font of ccp views
     *
     * @param typeFace
     */
    fun setTypeFace(typeFace: Typeface) {
        try {
            textView_selectedCountry.typeface = typeFace
            setDialogTypeFace(typeFace)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * To change font of ccp views along with style.
     *
     * @param typeFace
     * @param style
     */
    fun setDialogTypeFace(typeFace: Typeface, style: Int) {
        var style = style
        try {
            dialogTypeFace = typeFace
            if (dialogTypeFace == null) {
                style = DEFAULT_UNSET
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * To change font of ccp views along with style.
     *
     * @param typeFace
     * @param style
     */
    fun setTypeFace(typeFace: Typeface, style: Int) {
        try {
            textView_selectedCountry.setTypeface(typeFace, style)
            setDialogTypeFace(typeFace, style)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * To get call back on country selection a onCountryChangeListener must be registered.
     *
     * @param onCountryChangeListener
     */
    fun setOnCountryChangeListener(onCountryChangeListener: OnCountryChangeListener) {
        this.onCountryChangeListener = onCountryChangeListener
    }

    /**
     * Modifies size of flag in CCP view
     *
     * @param flagSize size in pixels
     */
    fun setFlagSize(flagSize: Int) {
        imageViewFlag.layoutParams.height = flagSize
        imageViewFlag.requestLayout()
    }

    fun showFlag(showFlag: Boolean) {
        this.showFlag = showFlag
        refreshFlagVisibility()
        if (!isInEditMode)
            selectedCountry = selectedCCPCountry
    }

    private fun refreshFlagVisibility() {
        if (showFlag) {
            if (ccpUseEmoji) {
                linearFlagHolder.visibility = View.GONE
            } else {
                linearFlagHolder.visibility = View.VISIBLE
            }
        } else {
            linearFlagHolder.visibility = View.GONE
        }
    }

    fun useFlagEmoji(useFlagEmoji: Boolean) {
        this.ccpUseEmoji = useFlagEmoji
        refreshFlagVisibility()
        selectedCountry = selectedCCPCountry
    }

    fun showFullName(showFullName: Boolean) {
        this.showFullName = showFullName
        selectedCountry = selectedCCPCountry
    }

    /**
     * Sets validity change listener.
     * First call back will be sent right away.
     *
     * @param phoneNumberValidityChangeListener
     */
    fun setPhoneNumberValidityChangeListener(phoneNumberValidityChangeListener: PhoneNumberValidityChangeListener) {
        this.phoneNumberValidityChangeListener = phoneNumberValidityChangeListener
        if (editText_registeredCarrierNumber != null) {
            reportedValidity = isValidFullNumber
            phoneNumberValidityChangeListener.onValidityChanged(reportedValidity)
        }
    }

    /**
     * Sets failure listener.
     *
     * @param failureListener
     */
    fun setAutoDetectionFailureListener(failureListener: FailureListener) {
        this.failureListener = failureListener
    }

    /**
     * If developer wants to change CCP Dialog's Title, Search Hint text or no result ACK,
     * a custom dialog text provider should be set.
     *
     * @param customDialogTextProvider
     */
    fun setCustomDialogTextProvider(customDialogTextProvider: CustomDialogTextProvider) {
        this.customDialogTextProvider = customDialogTextProvider
    }

    /**
     * Manually trigger selection dialog and set
     * scroll position to specified country.
     */
    @JvmOverloads
    fun launchCountrySelectionDialog(countryNameCode: String? = null) {
        CountryCodeDialog.openCountryCodeDialog(codePicker, countryNameCode)
    }

    private fun getPhoneUtil(): PhoneNumberUtil? {
        if (phoneUtil == null) {
            phoneUtil = PhoneNumberUtil.createInstance(mContext)
        }
        return phoneUtil
    }

    /**
     * loads current country in ccp using locale and telephony manager
     * this will follow specified order in countryAutoDetectionPref
     *
     * @param loadDefaultWhenFails: if all of pref methods fail to detect country then should this
     * function load default country or not is decided with this flag
     */
    fun setAutoDetectedCountry(loadDefaultWhenFails: Boolean) {
        try {
            var successfullyDetected = false
            for (i in 0 until selectedAutoDetectionPref.representation.length) {
                when (selectedAutoDetectionPref.representation[i]) {
                    '1' -> successfullyDetected = detectSIMCountry(false)
                    '2' -> successfullyDetected = detectNetworkCountry(false)
                    '3' -> successfullyDetected = detectLocaleCountry(false)
                }
                if (successfullyDetected) {
                    break
                } else {
                    if (failureListener != null) {
                        failureListener!!.onCountryAutoDetectionFailed()
                    }
                }
            }

            if (!successfullyDetected && loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w(TAG, "setAutoDetectCountry: Exception" + e.message)
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
        }

    }

    /**
     * This will detect country from SIM info and then load it into CCP.
     *
     * @param loadDefaultWhenFails true if want to reset to default country when sim country cannot be detected. if false, then it
     * will not change currently selected country
     * @return true if it successfully sets country, false otherwise
     */
    fun detectSIMCountry(loadDefaultWhenFails: Boolean): Boolean {
        try {
            val telephonyManager = mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountryISO = telephonyManager.simCountryIso
            if (simCountryISO == null || simCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry()
                }
                return false
            }
            selectedCountry =
                CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(),  getLanguageToApply()!!, simCountryISO)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
            return false
        }

    }

    /**
     * This will detect country from NETWORK info and then load it into CCP.
     *
     * @param loadDefaultWhenFails true if want to reset to default country when network country cannot be detected. if false, then it
     * will not change currently selected country
     * @return true if it successfully sets country, false otherwise
     */
    fun detectNetworkCountry(loadDefaultWhenFails: Boolean): Boolean {
        try {
            val telephonyManager = mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkCountryISO = telephonyManager.networkCountryIso
            if (networkCountryISO == null || networkCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry()
                }
                return false
            }
            selectedCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                getContext(),
                 getLanguageToApply()!!,
                networkCountryISO
            )
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
            return false
        }

    }

    /**
     * This will detect country from LOCALE info and then load it into CCP.
     *
     * @param loadDefaultWhenFails true if want to reset to default country when locale country cannot be detected. if false, then it
     * will not change currently selected country
     * @return true if it successfully sets country, false otherwise
     */
    fun detectLocaleCountry(loadDefaultWhenFails: Boolean): Boolean {
        try {
            val localeCountryISO = mContext.resources.configuration.locale.country
            if (localeCountryISO == null || localeCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry()
                }
                return false
            }
            selectedCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                getContext(),
                 getLanguageToApply()!!,
                localeCountryISO
            )
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
            return false
        }

    }

    /**
     * This will update the pref for country auto detection.
     * Remeber, this will not call setAutoDetectedCountry() to update country. This must be called separately.
     *
     * @param selectedAutoDetectionPref new detection pref
     */
    fun setCountryAutoDetectionPref(selectedAutoDetectionPref: AutoDetectionPref) {
        this.selectedAutoDetectionPref = selectedAutoDetectionPref
    }

    fun onUserTappedCountry(CCPCountry: CCPCountry) {
        if (codePicker.rememberLastSelection) {
            codePicker.storeSelectedCountryNameCode(CCPCountry.nameCode)
        }
        selectedCountry = CCPCountry
    }

    fun setDetectCountryWithAreaCode(detectCountryWithAreaCode: Boolean) {
        this.detectCountryWithAreaCode = detectCountryWithAreaCode
        updateFormattingTextWatcher()
    }

    fun setHintExampleNumberEnabled(hintExampleNumberEnabled: Boolean) {
        this.hintExampleNumberEnabled = hintExampleNumberEnabled
        updateHint()
    }

    fun setHintExampleNumberType(hintExampleNumberType: PhoneNumberType) {
        this.hintExampleNumberType = hintExampleNumberType
        updateHint()
    }

    /**
     * This will decide initial scroll position of countries list in dialog.
     *
     * @param initialScrollToSelection : false -> show list without any scroll
     * true -> will scroll to the position of the selected country.
     * Note: if selected country is a preferred country,
     * then it will not scroll and show full preferred countries list.
     */
    fun enableDialogInitialScrollToSelection(initialScrollToSelection: Boolean) {
        this.isDialogInitialScrollToSelectionEnabled = isDialogInitialScrollToSelectionEnabled
    }

    /**
     * To listen to the click handle action manually,
     * a custom clicklistener must be set.
     * This will override the default click listener which opens the selection dialog.
     *
     * @param clickListener will start receiving click callbacks. If null then default click listener
     * will receive callback and selection dialog will be prompted.
     */
    fun overrideClickListener(clickListener: View.OnClickListener) {
        customClickListener = clickListener
    }

    /**
     * Update every time new language is supported #languageSupport
     */
    //add an entry for your language in attrs.xml's <attr name="language" format="enum"> enum.
    //add here so that language can be set programmatically
    enum class Language {
        AFRIKAANS("af"),
        ARABIC("ar"),
        BENGALI("bn"),
        CHINESE_SIMPLIFIED("zh", "CN", "Hans"),
        CHINESE_TRADITIONAL("zh", "TW", "Hant"),
        CZECH("cs"),
        DANISH("da"),
        DUTCH("nl"),
        ENGLISH("en"),
        FARSI("fa"),
        FRENCH("fr"),
        GERMAN("de"),
        GREEK("el"),
        GUJARATI("gu"),
        HEBREW("iw"),
        HINDI("hi"),
        INDONESIA("in"),
        ITALIAN("it"),
        JAPANESE("ja"),
        KOREAN("ko"),
        POLISH("pl"),
        PORTUGUESE("pt"),
        PUNJABI("pa"),
        RUSSIAN("ru"),
        SLOVAK("sk"),
        SPANISH("es"),
        SWEDISH("sv"),
        TURKISH("tr"),
        UKRAINIAN("uk"),
        UZBEK("uz"),
        VIETNAMESE("vi");

        var code: String? = null
        var country: String? = null
        var script: String? = null

        private constructor(code: String, country: String, script: String) {
            this.code = code
            this.country = country
            this.script = script
        }

        private constructor(code: String) {
            this.code = code
        }
    }

    enum class PhoneNumberType {
        MOBILE,
        FIXED_LINE,
        // In some regions (e.g. the USA), it is impossible to distinguish between fixed-line and
        // mobile numbers by looking at the phone number itself.
        FIXED_LINE_OR_MOBILE,
        // Freephone lines
        TOLL_FREE,
        PREMIUM_RATE,
        // The cost of this call is shared between the caller and the recipient, and is hence typically
        // less than PREMIUM_RATE calls. See // http://en.wikipedia.org/wiki/Shared_Cost_Service for
        // more information.
        SHARED_COST,
        // Voice over IP numbers. This includes TSoIP (Telephony Service over IP).
        VOIP,
        // A personal number is associated with a particular person, and may be routed to either a
        // MOBILE or FIXED_LINE number. Some more information can be found here:
        // http://en.wikipedia.org/wiki/Personal_Numbers
        PERSONAL_NUMBER,
        PAGER,
        // Used for "Universal Access Numbers" or "Company Numbers". They may be further routed to
        // specific offices, but allow one number to be used for a company.
        UAN,
        // Used for "Voice Mail Access Numbers".
        VOICEMAIL,
        // A phone number is of type UNKNOWN when it does not fit any of the known patterns for a
        // specific region.
        UNKNOWN
    }

    enum class AutoDetectionPref private constructor(private var representation: String) {
        SIM_ONLY("1"),
        NETWORK_ONLY("2"),
        LOCALE_ONLY("3"),
        SIM_NETWORK("12"),
        NETWORK_SIM("21"),
        SIM_LOCALE("13"),
        LOCALE_SIM("31"),
        NETWORK_LOCALE("23"),
        LOCALE_NETWORK("32"),
        SIM_NETWORK_LOCALE("123"),
        SIM_LOCALE_NETWORK("132"),
        NETWORK_SIM_LOCALE("213"),
        NETWORK_LOCALE_SIM("231"),
        LOCALE_SIM_NETWORK("312"),
        LOCALE_NETWORK_SIM("321");


        companion object {

            fun getPrefForValue(value: String): AutoDetectionPref {
                for (autoDetectionPref in AutoDetectionPref.values()) {
                    if (autoDetectionPref.representation == value) {
                        return autoDetectionPref
                    }
                }
                return SIM_NETWORK_LOCALE
            }
        }
    }

    /**
     * When width is "match_parent", this gravity will decide the placement of text.
     */
    enum class TextGravity private constructor(var enumIndex: Int) {
        LEFT(-1), CENTER(0), RIGHT(1)
    }

    /**
     * interface to set change listener
     */
    interface OnCountryChangeListener {
        fun onCountrySelected()
    }

    /**
     * interface to listen to failure events
     */
    interface FailureListener {
        //when country auto detection failed.
        fun onCountryAutoDetectionFailed()
    }

    /**
     * Interface to check phone number validity change listener
     */
    interface PhoneNumberValidityChangeListener {
        fun onValidityChanged(isValidNumber: Boolean)
    }

    interface DialogEventsListener {
        fun onCcpDialogOpen(dialog: Dialog)

        fun onCcpDialogDismiss(dialogInterface: DialogInterface)

        fun onCcpDialogCancel(dialogInterface: DialogInterface)
    }

    interface CustomDialogTextProvider {
        fun getCCPDialogTitle(language: Language?, defaultTitle: String): String

        fun getCCPDialogSearchHintText(language: Language?, defaultSearchHintText: String): String

        fun getCCPDialogNoResultACK(language: Language?, defaultNoResultACK: String): String
    }

    override fun onDetachedFromWindow() {
        CountryCodeDialog.clear()
        super.onDetachedFromWindow()
    }

    companion object {

        val DEFAULT_UNSET = -99
        private var TAG = "CCP"
        private var BUNDLE_SELECTED_CODE = "selectedCode"
        private var LIB_DEFAULT_COUNTRY_CODE = 91
        private val TEXT_GRAVITY_LEFT = -1
        private val TEXT_GRAVITY_RIGHT = 1
        private val TEXT_GRAVITY_CENTER = 0
        private val ANDROID_NAME_SPACE = "http://schemas.android.com/apk/res/android"
    }
}
/**
 * Opens country selection dialog.
 * By default this is called from ccp click.
 * Developer can use this to trigger manually.
 */

