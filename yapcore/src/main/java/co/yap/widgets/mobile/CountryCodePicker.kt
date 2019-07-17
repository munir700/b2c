package co.yap.widgets.mobile


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import android.widget.*
import co.yap.yapcore.R
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*


class CountryCodePicker : RelativeLayout {
    private var CCP_PREF_FILE = "CCP_PREF_FILE"
    private var defaultCountryCode: Int = 0
    private var defaultCountryNameCode: String? = null
    var mCntext: Context = getContext()

    private var holderView: View? = null
    private var mInflater: LayoutInflater? = null
    var textView_selectedCountry: TextView? = null
    private var editText_registeredCarrierNumber: EditText? = null
    var holder: RelativeLayout? = null
        private set
    private var imageViewArrow: ImageView? = null
    var imageViewFlag: ImageView? = null
    private var linearFlagBorder: LinearLayout? = null
    private var linearFlagHolder: LinearLayout? = null
    private var selectedCCPCountry: CCPCountry? = null

    internal var defaultCCPCountry: CCPCountry? = null

    private// Log.d(TAG, "Setting default country:" + defaultCountry.logString());
//    var defaultCountry: CCPCountry? = null
      var relativeClickConsumer: RelativeLayout? = null
    private var codePicker: CountryCodePicker? = null
    private lateinit var currentTextGravity: TextGravity
    // see attr.xml to see corresponding values for pref
    private var selectedAutoDetectionPref = AutoDetectionPref.SIM_NETWORK_LOCALE
    private var phoneUtil: PhoneNumberUtil? = null
    private var showNameCode = true
    private var showPhoneCode = true


    var isCcpDialogShowPhoneCode = true
        set
    private var showFlag = true
    private var showFullName = false

    var isShowFastScroller = true
        set

    var ccpDialogShowTitle = true

    var ccpDialogShowFlag = true

    var isSearchAllowed = true
    private var showArrow = true
    var isShowCloseIcon = false
        private set
    private var rememberLastSelection = false
    private var detectCountryWithAreaCode = true

    var ccpDialogShowNameCode = true
    var isDialogInitialScrollToSelectionEnabled = false
        private set
    var ccpUseEmoji = false
    private var ccpUseDummyEmojiForPreview = false
    private var isInternationalFormattingOnlyEnabled = true
        private set
    private var hintExampleNumberType = PhoneNumberType.MOBILE
    private var selectionMemoryTag = "ccp_last_selection"
    private var contentColor = DEFAULT_UNSET
    private var arrowColor = DEFAULT_UNSET
    private var borderFlagColor: Int = 0
    private var dialogTypeFace: Typeface? = null
//    var dialogTypeFaceStyle: Int = 0
    var preferredCountries: List<CCPCountry>? = null
    private var ccpTextgGravity = TEXT_GRAVITY_CENTER
    //this will be "AU,IN,US"
    private var countryPreference: String? = null

    var fastScrollerBubbleColor = 0
        set

    var customMasterCountriesList: List<CCPCountry>? = null

    private var customMasterCountriesParam: String? = null
    private var excludedCountriesParam: String? = null
    private var customDefaultLanguage = Language.ENGLISH
    private var languageToApply = Language.ENGLISH

    var isDialogKeyboardAutoPopup = true
        set
    private var ccpClickable = true
    private var isAutoDetectLanguageEnabled = false
    private var isAutoDetectCountryEnabled = false
    private var numberAutoFormattingEnabled = true
    private var hintExampleNumberEnabled = false
    private var xmlWidth = "notSet"
    private var validityTextWatcher: TextWatcher? = null
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

    var dialogEventsListener: DialogEventsListener? = null
        set
    private var customDialogTextProvider: CustomDialogTextProvider? = null

    var fastScrollerHandleColor = 0
        set

    var dialogBackgroundColor: Int = 0
        set

    var dialogTextColor: Int = 0
        set

    var dialogSearchEditTextTintColor: Int = 0
        set

    var fastScrollerBubbleTextAppearance = 0
        set
    private var currentCountryGroup: CCPCountryGroup? = null
    private var customClickListener: View.OnClickListener? = null
    private var countryCodeHolderClickListener: OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
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
    }
        private set

    private var isNumberAutoFormattingEnabled: Boolean
        get() {
            return numberAutoFormattingEnabled
        }
        set(numberAutoFormattingEnabled) {
            this.numberAutoFormattingEnabled = numberAutoFormattingEnabled
            if (editText_registeredCarrierNumber != null) {
                updateFormattingTextWatcher()
            }
        }

    private var isShowPhoneCode: Boolean
        get() {
            return showPhoneCode
        }
        set(showPhoneCode) {
            this.showPhoneCode = showPhoneCode
            selectedCountry = this!!.selectedCCPCountry!!
        }
    private// Log.d(TAG, "getCCPLanguageFromLocale: current locale language" + currentLocale.getLanguage());
    val ccpLanguageFromLocale: Language?
        get() {
            val currentLocale = mCntext.getResources().getConfiguration().locale
            for (language in Language.values()) {
                if (language.code.equals(currentLocale.getLanguage(), ignoreCase = true)) {
                    if ((language.country == null || language.country.equals(
                            currentLocale.getCountry(),
                            ignoreCase = true
                        ))
                    )
                        return language
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if ((language.script == null || language.script.equals(
                                currentLocale.getScript(),
                                ignoreCase = true
                            ))
                        )
                            return language
                    }
                }
            }
            return null
        }
    private var selectedCountry: CCPCountry ?= null
        //        get() {
//            if (selectedCCPCountry == null) {
//                selectedCountry = defaultCountry!!
//            }
//            return selectedCCPCountry!!
//        }
        private set(selectedCCPCountry) {
            setSelectedCountry(selectedCCPCountry)
        }/*{
            countryDetectionBasedOnAreaAllowed = false
            lastCheckedAreaCode = ""
            if (selectedCCPCountry == null) {
                this.selectedCCPCountry = CCPCountry.getCountryForCode(
                    getContext(), getLanguageToApply(),
                    this!!.preferredCountries!!, defaultCountryCode
                )!!
                if (selectedCCPCountry == null) {
                    return
                }
            }
            this.selectedCCPCountry = selectedCCPCountry
            var displayText = ""
            if (showFlag && ccpUseEmoji) {
                if (isInEditMode()) {
                    if (ccpUseDummyEmojiForPreview) {
                        displayText += "\uD83C\uDFC1\u200B "
                    } else {
                        displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "\u200B "
                    }
                } else {
                    displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + " "
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
                    displayText += " "
                }
                displayText += "+" + selectedCCPCountry.phoneCode
            }
            textView_selectedCountry!!.setText(displayText)
            if (showFlag == false && displayText.length == 0) {
                displayText += "+" + selectedCCPCountry.phoneCode
                textView_selectedCountry!!.setText(displayText)
            }
            if (onCountryChangeListener != null) {
                onCountryChangeListener!!.onCountrySelected()
            }
            imageViewFlag!!.setImageResource(selectedCCPCountry.flagID)
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
        }*/

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

    private val countryDetectorTextWatcher: TextWatcher
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
                                    val enteredValue = getEditText_registeredCarrierNumber().getText().toString()
                                    if (enteredValue.length >= currentCountryGroup!!.areaCodeLength) {
                                        val digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue)
                                        if (digitsValue.length >= currentCountryGroup!!.areaCodeLength) {
                                            val currentAreaCode =
                                                digitsValue.substring(0, currentCountryGroup!!.areaCodeLength)
                                            if (currentAreaCode != lastCheckedAreaCode) {
                                                val detectedCountry = currentCountryGroup!!.getCountryForAreaCode(
                                                    mCntext,
                                                    getLanguageToApply(),
                                                    currentAreaCode
                                                )
                                                if (!detectedCountry.equals(selectedCountry)) {
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

    private var isCcpClickable: Boolean
        get() {
            return ccpClickable
        }
        set(ccpClickable) {
            this.ccpClickable = ccpClickable
            if (!ccpClickable) {
                relativeClickConsumer!!.setOnClickListener(null)
                relativeClickConsumer!!.setClickable(false)
                relativeClickConsumer!!.setEnabled(false)
            } else {
                relativeClickConsumer!!.setOnClickListener(countryCodeHolderClickListener)
                relativeClickConsumer!!.setClickable(true)
                relativeClickConsumer!!.setEnabled(true)
            }
        }

    val dialogTitle: String
        get() {
            val defaultTitle = CCPCountry.getDialogTitle(mCntext, getLanguageToApply())
            if (customDialogTextProvider != null) {
                return customDialogTextProvider!!.getCCPDialogTitle(getLanguageToApply(), defaultTitle!!)
            } else {
                return defaultTitle!!
            }
        }

    val searchHintText: String
        get() {
            val defaultHint = CCPCountry.getSearchHintMessage(mCntext, getLanguageToApply())
            if (customDialogTextProvider != null) {
                return customDialogTextProvider!!.getCCPDialogSearchHintText(getLanguageToApply(), defaultHint!!)
            } else {
                return defaultHint!!
            }
        }

    val noResultACK: String
        get() {
            val defaultNoResultACK = CCPCountry.getNoResultFoundAckMessage(mCntext, getLanguageToApply())
            if (customDialogTextProvider != null) {
                return customDialogTextProvider!!.getCCPDialogNoResultACK(getLanguageToApply(), defaultNoResultACK!!)
            } else {
                return defaultNoResultACK!!
            }
        }

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

    val defaultCountryCodeWithPlus: String
        get() {
            return "+" + getDefaultCountryCode()
        }


    val defaultCountryName: String
        get() {
            return defaultCCPCountry!!.name
        }

    val selectedCountryCode: String
        get() {
            return selectedCountry!!.phoneCode
        }

    val selectedCountryCodeWithPlus: String
        get() {
            return "+" + selectedCountryCode
        }

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

    val selectedCountryName: String
        get() {
            return selectedCountry!!.name
        }

    val selectedCountryEnglishName: String
        get() {
            return selectedCountry!!.englishName
        }

    val selectedCountryNameCode: String
        get() {
            return selectedCountry!!.nameCode.toUpperCase()
        }
    private val enteredPhoneNumber: Phonenumber.PhoneNumber
        @Throws(NumberParseException::class)
        get() {
            var carrierNumber = ""
            if (editText_registeredCarrierNumber != null) {
                carrierNumber =
                    PhoneNumberUtil.normalizeDigitsOnly(editText_registeredCarrierNumber!!.getText().toString())
            }
            return getPhoneUtil().parse(carrierNumber, selectedCountryNameCode)
        }

    var fullNumber: String
        get() {
            try {
                val phoneNumber = enteredPhoneNumber
                return getPhoneUtil().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164).substring(1)
            } catch (e: NumberParseException) {
                Log.e(TAG, "getFullNumber: Could not parse number")
                return selectedCountryCode
            }
        }
        set(fullNumber) {
            var country =
                CCPCountry.getCountryForNumber(getContext(), getLanguageToApply(), preferredCountries, fullNumber)
            if (country == null)
                country = defaultCCPCountry
            selectedCountry = country!!
            val carrierNumber = detectCarrierNumber(fullNumber, country!!)
            if (getEditText_registeredCarrierNumber() != null) {
                getEditText_registeredCarrierNumber().setText(carrierNumber)
                updateFormattingTextWatcher()
            } else {
                Log.w(
                    TAG,
                    "EditText for carrier number is not registered. Register it using registerCarrierNumberEditText() before getFullNumber() or setFullNumber()."
                )
            }
        }

    val formattedFullNumber: String
        get() {
            try {
                val phoneNumber = enteredPhoneNumber
                return "+" + getPhoneUtil().format(
                    phoneNumber,
                    PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
                ).substring(1)
            } catch (e: NumberParseException) {
                Log.e(TAG, "getFullNumber: Could not parse number")
                return selectedCountryCode
            }
        }

    val fullNumberWithPlus: String
        get() {
            try {
                val phoneNumber = enteredPhoneNumber
                return getPhoneUtil().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
            } catch (e: NumberParseException) {
                Log.e(TAG, "getFullNumber: Could not parse number")
                return selectedCountryCode
            }
        }

    val isValidFullNumber: Boolean
        get() {
            try {
                if (getEditText_registeredCarrierNumber() != null && getEditText_registeredCarrierNumber().getText().length !== 0) {
                    val phoneNumber = getPhoneUtil().parse(
                        "+" + selectedCCPCountry!!.phoneCode + getEditText_registeredCarrierNumber().getText().toString(),
                        selectedCCPCountry!!.nameCode
                    )
                    return getPhoneUtil().isValidNumber(phoneNumber)
                } else if (getEditText_registeredCarrierNumber() == null) {
                    Toast.makeText(mCntext, "No editText for Carrier number found.", Toast.LENGTH_SHORT).show()
                    return false
                } else {
                    return false
                }
            } catch (e: NumberParseException) {
                return false
            }
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mCntext = context
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.mCntext = context
        init(attrs)
    }

    fun setInternationalFormattingOnly(internationalFormattingOnly: Boolean) {
        this.isInternationalFormattingOnlyEnabled = internationalFormattingOnly
        if (editText_registeredCarrierNumber != null) {
            updateFormattingTextWatcher()
        }
    }

    private fun init(attrs: AttributeSet?) {
        mInflater = LayoutInflater.from(context)
        if (attrs != null) {
            xmlWidth = attrs.getAttributeValue(ANDROID_NAME_SPACE, "layout_width")
        }
        removeAllViewsInLayout()
        //at run time, match parent value returns LayoutParams.MATCH_PARENT ("-1"), for some android xml preview it returns "fill_parent"
        if (attrs != null && xmlWidth != null && (xmlWidth == LayoutParams.MATCH_PARENT.toString() + "" || xmlWidth == LayoutParams.FILL_PARENT.toString() + "" || xmlWidth == "fill_parent" || xmlWidth == "match_parent")) {
            holderView = this.mInflater!!.inflate(R.layout.layout_full_width_code_picker, this, true)
        } else {
            holderView = this.mInflater!!.inflate(R.layout.layout_code_picker, this, true)
        }
        textView_selectedCountry = this.holderView!!.findViewById(R.id.textView_selectedCountry) as TextView
        holder = this.holderView!!.findViewById(R.id.countryCodeHolder) as RelativeLayout
        imageViewArrow = this.holderView!!.findViewById(R.id.imageView_arrow) as ImageView
        imageViewFlag = this.holderView!!.findViewById(R.id.image_flag) as ImageView
        linearFlagHolder = this.holderView!!.findViewById(R.id.linear_flag_holder) as LinearLayout
        linearFlagBorder = this.holderView!!.findViewById(R.id.linear_flag_border) as LinearLayout
        relativeClickConsumer = this.holderView!!.findViewById(R.id.rlClickConsumer) as RelativeLayout
        codePicker = this
        if (attrs != null) {
            applyCustomProperty(attrs)
        }
        relativeClickConsumer!!.setOnClickListener(countryCodeHolderClickListener)
    }

    private fun applyCustomProperty(attrs: AttributeSet) {
        val a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0)
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
            selectedAutoDetectionPref = AutoDetectionPref.getPrefForValue((autoDetectionPrefValue).toString())
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
            if (!isInEditMode()) {
                refreshCustomMasterList()
            }
            //preference
            countryPreference = a.getString(R.styleable.CountryCodePicker_ccp_countryPreference)
            //as3 is raising problem while rendering preview. to avoid such issue, it will update preferred list only on run time.
            if (!isInEditMode()) {
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
                if (!isInEditMode()) {
                    if (CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                            getContext(), getLanguageToApply(),
                            defaultCountryNameCode!!
                        ) != null
                    ) {
                        setUsingNameCode = true
                        defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                            getContext(), getLanguageToApply(),
                            defaultCountryNameCode!!
                        )
                        selectedCountry = this!!.defaultCCPCountry!!
                    }
                } else {
                    if (CCPCountry.getCountryForNameCodeFromEnglishList(defaultCountryNameCode!!) != null) {
                        setUsingNameCode = true
                        defaultCCPCountry = CCPCountry.getCountryForNameCodeFromEnglishList(defaultCountryNameCode!!)
                        selectedCountry = this!!.defaultCCPCountry!!
                    }
                }
                //when it was not set means something was wrong with name code
                if (!setUsingNameCode) {
                    defaultCCPCountry = CCPCountry.getCountryForNameCodeFromEnglishList("IN")
                    selectedCountry = this!!.defaultCCPCountry!!
                    setUsingNameCode = true
                }
            }
            //if default country is not set using name code.
            var defaultCountryCode = a.getInteger(R.styleable.CountryCodePicker_ccp_defaultPhoneCode, -1)
            if (!setUsingNameCode && defaultCountryCode != -1) {
                if (!isInEditMode()) {
                    //if invalid country is set using xml, it will be replaced with LIB_DEFAULT_COUNTRY_CODE
                    if (defaultCountryCode != -1 && CCPCountry.getCountryForCode(
                            getContext(), getLanguageToApply(),
                            this!!.preferredCountries!!, defaultCountryCode
                        ) == null
                    ) {
                        defaultCountryCode = LIB_DEFAULT_COUNTRY_CODE
                    }
                    setDefaultCountryUsingPhoneCode(defaultCountryCode)
                    selectedCountry = defaultCCPCountry!!
                } else {
                    //when it is in edit mode, we will check in english list only.
                    var defaultCountry = CCPCountry.getCountryForCodeFromEnglishList(defaultCountryCode.toString() + "")
                    if (defaultCCPCountry == null) {
                        defaultCountry =
                            CCPCountry.getCountryForCodeFromEnglishList(LIB_DEFAULT_COUNTRY_CODE.toString() + "")
                    }
                    defaultCCPCountry = defaultCountry
                    selectedCountry = defaultCountry!!
                }
            }
            //if default country is not set using nameCode or phone code, let's set library default as default
            if (defaultCCPCountry == null) {
                defaultCCPCountry = CCPCountry.getCountryForNameCodeFromEnglishList("IN")
                if (selectedCountry == null) {
                    selectedCountry = defaultCCPCountry!!
                }
            }
            //set auto detected country
            if (isAutoDetectCountryEnabled && !isInEditMode()) {
                setAutoDetectedCountry(true)
            }
            //set last selection
            if (rememberLastSelection && !isInEditMode()) {
                loadLastSelectedCountryInCCP()
            }
            val arrowColor: Int
            arrowColor = a.getColor(R.styleable.CountryCodePicker_ccp_arrowColor, DEFAULT_UNSET)
            setArrowColor(arrowColor)
            //content color
            val contentColor: Int
            if (isInEditMode()) {
                contentColor = a.getColor(R.styleable.CountryCodePicker_ccp_contentColor, DEFAULT_UNSET)
            } else {
                contentColor = a.getColor(
                    R.styleable.CountryCodePicker_ccp_contentColor,
                    context.getResources().getColor(R.color.warning)
                )
            }
            if (contentColor != DEFAULT_UNSET) {
                setContentColor(contentColor)
            }
            // flag border color
            val borderFlagColor: Int
            if (isInEditMode()) {
                borderFlagColor = a.getColor(R.styleable.CountryCodePicker_ccp_flagBorderColor, 0)
            } else {
                borderFlagColor = a.getColor(
                    R.styleable.CountryCodePicker_ccp_flagBorderColor,
                    context.getResources().getColor(R.color.warning)
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
                textView_selectedCountry!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
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
        }


        catch (e: Exception) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            textView_selectedCountry!!.setMaxLines(25)
            textView_selectedCountry!!.setTextSize(10F)
            textView_selectedCountry!!.setText(sw.toString())
        } finally {
            a.recycle()
        }
    }

    private fun refreshArrowViewVisibility() {
        if (showArrow) {
            imageViewArrow!!.setVisibility(VISIBLE)
        } else {
            imageViewArrow!!.setVisibility(GONE)
        }
    }

    private fun loadLastSelectedCountryInCCP() {
        //get the shared pref
        val sharedPref = context.getSharedPreferences(
            CCP_PREF_FILE, Context.MODE_PRIVATE
        )
        // read last selection value
        val lastSelectedCountryNameCode = sharedPref.getString(selectionMemoryTag, null)
        //if last selection value is not null, load it into the CCP
        if (lastSelectedCountryNameCode != null) {
            setCountryForNameCode(lastSelectedCountryNameCode)
        }
    }

    private fun storeSelectedCountryNameCode(selectedCountryNameCode: String) {
        val sharedPref = context.getSharedPreferences(
            CCP_PREF_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString(selectionMemoryTag, selectedCountryNameCode)
        editor.apply()
    }

    private fun getCurrentTextGravity(): TextGravity {
        return currentTextGravity
    }

    fun setCurrentTextGravity(textGravity: TextGravity) {
        this.currentTextGravity = textGravity
        applyTextGravity(textGravity.enumIndex)
    }

    private fun applyTextGravity(enumIndex: Int) {
        if (enumIndex == TextGravity.LEFT.enumIndex) {
            textView_selectedCountry!!.setGravity(Gravity.LEFT)
        } else if (enumIndex == TextGravity.CENTER.enumIndex) {
            textView_selectedCountry!!.setGravity(Gravity.CENTER)
        } else {
            textView_selectedCountry!!.setGravity(Gravity.RIGHT)
        }
    }

    private fun updateLanguageToApply() {
        if (isInEditMode()) {
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
                    languageToApply = Language.ENGLISH //library default
                }
            }
        }
    }

    private fun updateCountryGroup() {
//        currentCountryGroup = currentCountryGroup!!.getCountryGroupForPhoneCode(selectedCountryCodeAsInt)
//        currentCountryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(selectedCountryCodeAsInt)
    }

    private fun updateHint() {
        if (editText_registeredCarrierNumber != null && hintExampleNumberEnabled) {
            var formattedNumber = ""
            val exampleNumber = getPhoneUtil().getExampleNumberForType(selectedCountryNameCode, selectedHintNumberType)
            if (exampleNumber != null) {
                formattedNumber = exampleNumber.getNationalNumber().toString() + ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    formattedNumber = PhoneNumberUtils.formatNumber(
                        selectedCountryCodeWithPlus + formattedNumber,
                        selectedCountryNameCode
                    )
                } else {
                    formattedNumber = PhoneNumberUtils.formatNumber(selectedCountryCodeWithPlus + formattedNumber)
                }
                formattedNumber = formattedNumber.substring(selectedCountryCodeWithPlus.length).trim({ it <= ' ' })
            } else {
                // Log.w(TAG, "updateHint: No example number found for this country (" + getSelectedCountryNameCode() + ") or this type (" + hintExampleNumberType.name() + ").");
            }
            editText_registeredCarrierNumber!!.setHint(formattedNumber)
        }
    }

    fun getLanguageToApply(): Language {
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
            val enteredValue = getEditText_registeredCarrierNumber().getText().toString()
            val digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue)
            if (formattingTextWatcher != null) {
                editText_registeredCarrierNumber!!.removeTextChangedListener(formattingTextWatcher)
            }
            if (areaCodeCountryDetectorTextWatcher != null) {
                editText_registeredCarrierNumber!!.removeTextChangedListener(areaCodeCountryDetectorTextWatcher)
            }
            if (numberAutoFormattingEnabled) {
                formattingTextWatcher = InternationalPhoneTextWatcher(
                    context,
                    selectedCountryNameCode,
                    selectedCountryCodeAsInt,
                    isInternationalFormattingOnlyEnabled
                )
                editText_registeredCarrierNumber!!.addTextChangedListener(formattingTextWatcher)
            }
            if (detectCountryWithAreaCode) {
                areaCodeCountryDetectorTextWatcher = countryDetectorTextWatcher
                editText_registeredCarrierNumber!!.addTextChangedListener(areaCodeCountryDetectorTextWatcher)
            }
            editText_registeredCarrierNumber!!.setText("")
            editText_registeredCarrierNumber!!.setText(digitsValue)
            editText_registeredCarrierNumber!!.setSelection(editText_registeredCarrierNumber!!.getText().length)
        } else {
            if (editText_registeredCarrierNumber == null) {
                Log.v(TAG, "updateFormattingTextWatcher: EditText not registered " + selectionMemoryTag)
            } else {
                Log.v(TAG, "updateFormattingTextWatcher: selected country is null " + selectionMemoryTag)
            }
        }
    }

    private fun getCustomDefaultLanguage(): Language {
        return customDefaultLanguage
    }

    private fun setCustomDefaultLanguage(customDefaultLanguage: Language) {
        this.customDefaultLanguage = customDefaultLanguage
        updateLanguageToApply()
        selectedCountry =
            CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                context,
                getLanguageToApply(),
                selectedCCPCountry!!.nameCode
            )!!
    }

    fun showCloseIcon(showCloseIcon: Boolean) {
        this.isShowCloseIcon = showCloseIcon
    }

    private fun getEditText_registeredCarrierNumber(): EditText {
        // Log.d(TAG, "getEditText_registeredCarrierNumber");
        return this!!.editText_registeredCarrierNumber!!
    }

    private fun setEditText_registeredCarrierNumber(editText_registeredCarrierNumber: EditText) {
        this.editText_registeredCarrierNumber = editText_registeredCarrierNumber
        // Log.d(TAG, "setEditText_registeredCarrierNumber: carrierEditTextAttached " + selectionMemoryTag);
        updateValidityTextWatcher()
        updateFormattingTextWatcher()
        updateHint()
    }

    private fun updateValidityTextWatcher() {
        try {
            editText_registeredCarrierNumber!!.removeTextChangedListener(validityTextWatcher)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        return this!!.mInflater!!
    }

//    fun getDialogTypeFace(): Typeface {
//        return this!!.dialogTypeFace!!
//    }

//    fun setDialogTypeFace(typeFace: Typeface) {
//        try {
//            dialogTypeFace = typeFace
//            dialogTypeFaceStyle = DEFAULT_UNSET
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    fun refreshPreferredCountries() {
        if (countryPreference == null || countryPreference!!.length == 0) {
            preferredCountries = null
        } else {
            val localCCPCountryList = ArrayList<CCPCountry>()
            for (nameCode in countryPreference!!.split((",").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()) {
                val ccpCountry = CCPCountry.getCountryForNameCodeFromCustomMasterList(
                    getContext(),
                    customMasterCountriesList,
                    getLanguageToApply(),
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
            for (CCPCountry in preferredCountries!!) {
                CCPCountry.log()
            }
        } else {
            // Log.d("preference list", " has no country");
        }
    }

    fun refreshCustomMasterList() {
        //if no custom list specified then check for exclude list
        if (customMasterCountriesParam == null || customMasterCountriesParam!!.length == 0) {
            //if excluded param is also blank, then do nothing
            if (excludedCountriesParam != null && excludedCountriesParam!!.length != 0) {
                excludedCountriesParam = excludedCountriesParam!!.toLowerCase()
                val libraryMasterList = CCPCountry.getLibraryMasterCountryList(context, getLanguageToApply())
                val localCCPCountryList = ArrayList<CCPCountry>()
                for (ccpCountry in libraryMasterList!!) {
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
            for (nameCode in customMasterCountriesParam!!.split((",").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()) {
                val ccpCountry =
                    CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), nameCode)
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
            // Log.d("custom master list:", customMasterCountriesList.size() + " countries");
            for (ccpCountry in customMasterCountriesList!!) {
                ccpCountry.log()
            }
        } else {
            // Log.d("custom master list", " has no country");
        }
    }

    fun setCustomMasterCountries(customMasterCountriesParam: String) {
        this.customMasterCountriesParam = customMasterCountriesParam
    }

    fun setExcludedCountries(excludedCountries: String) {
        this.excludedCountriesParam = excludedCountries
        refreshCustomMasterList()
    }

    private fun isAlreadyInList(CCPCountry: CCPCountry, CCPCountryList: List<CCPCountry>): Boolean {
        if (CCPCountry != null && CCPCountryList != null) {
            for (iterationCCPCountry in CCPCountryList) {
                if (iterationCCPCountry.nameCode.equals(CCPCountry.nameCode)) {
                    return true
                }
            }
        }
        return false
    }

    private fun detectCarrierNumber(fullNumber: String, CCPCountry: CCPCountry): String {
        val carrierNumber: String
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

    private fun getLanguageEnum(index: Int): Language {
        if (index < Language.values().size) {
            return Language.values()[index]
        } else {
            return Language.ENGLISH
        }
    }

    @Deprecated("")
    fun setDefaultCountryUsingPhoneCode(defaultCountryCode: Int) {
        val defaultCCPCountry = CCPCountry.getCountryForCode(
            getContext(), getLanguageToApply(),
            this!!.preferredCountries!!, defaultCountryCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            // Log.d(TAG, "No country for code " + defaultCountryCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryCode = defaultCountryCode
            this.defaultCCPCountry = defaultCCPCountry
        }
    }

    fun setDefaultCountryUsingNameCode(defaultCountryNameCode: String) {
        val defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            getContext(),
            getLanguageToApply(),
            defaultCountryNameCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            // Log.d(TAG, "No country for nameCode " + defaultCountryNameCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryNameCode = defaultCCPCountry.nameCode
            this.defaultCCPCountry = defaultCCPCountry
        }
    }

    fun getDefaultCountryCode(): String {
        return defaultCCPCountry!!.phoneCode
    }

    fun getDefaultCountryNameCode(): String {
        return defaultCCPCountry!!.nameCode.toUpperCase()
    }

    fun resetToDefaultCountry() {
        defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            getContext(),
            getLanguageToApply(),
            getDefaultCountryNameCode()
        )
        selectedCountry = defaultCCPCountry!!
    }

    fun setCountryForPhoneCode(countryCode: Int) {
        val ccpCountry = CCPCountry.getCountryForCode(
            getContext(), getLanguageToApply(),
            this!!.preferredCountries!!, countryCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (ccpCountry == null) {
            if (defaultCCPCountry == null) {
                defaultCCPCountry = CCPCountry.getCountryForCode(
                    getContext(), getLanguageToApply(),
                    this!!.preferredCountries!!, defaultCountryCode
                )
            }
            selectedCountry = this!!.defaultCCPCountry!!
        } else {
            selectedCountry = ccpCountry
        }
    }

    fun setCountryForNameCode(countryNameCode: String) {
        val country = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
            getContext(),
            getLanguageToApply(),
            countryNameCode
        ) //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (country == null) {
            if (defaultCCPCountry == null) {
                defaultCCPCountry = preferredCountries?.let {
                    CCPCountry.getCountryForCode(
                        getContext(), getLanguageToApply(),
                        it, defaultCountryCode
                    )
                }
            }
            selectedCountry = this!!.defaultCCPCountry!!
        } else {
            selectedCountry = country
        }
    }

    fun registerCarrierNumberEditText(editTextCarrierNumber: EditText) {
        setEditText_registeredCarrierNumber(editTextCarrierNumber)
    }

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
            editText_registeredCarrierNumber!!.setHint("")
            editText_registeredCarrierNumber = null
        }
    }

    fun getContentColor(): Int {
        return contentColor
    }

    fun setContentColor(contentColor: Int) {
        this.contentColor = contentColor
        textView_selectedCountry!!.setTextColor(this.contentColor)
        //change arrow color only if explicit arrow color is not specified.
        if (this.arrowColor == DEFAULT_UNSET) {
            imageViewArrow!!.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN)
        }
    }

    fun setArrowColor(arrowColor: Int) {
        this.arrowColor = arrowColor
        if (this.arrowColor == DEFAULT_UNSET) {
            if (contentColor != DEFAULT_UNSET) {
                imageViewArrow!!.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN)
            }
        } else {
            imageViewArrow!!.setColorFilter(this.arrowColor, PorterDuff.Mode.SRC_IN)
        }
    }

    fun setFlagBorderColor(borderFlagColor: Int) {
        this.borderFlagColor = borderFlagColor
        linearFlagBorder!!.setBackgroundColor(this.borderFlagColor)
    }

    fun setTextSize(textSize: Int) {
        if (textSize > 0) {
            textView_selectedCountry!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            setArrowSize(textSize)
            setFlagSize(textSize)
        }
    }

    fun setArrowSize(arrowSize: Int) {
        if (arrowSize > 0) {
            val params = imageViewArrow!!.getLayoutParams() as LayoutParams
            params.width = arrowSize
            params.height = arrowSize
            imageViewArrow!!.setLayoutParams(params)
        }
    }

    fun showNameCode(showNameCode: Boolean) {
        this.showNameCode = showNameCode
        selectedCountry = selectedCCPCountry!!
    }

    fun showArrow(showArrow: Boolean) {
        this.showArrow = showArrow
        refreshArrowViewVisibility()
    }

    fun setCountryPreference(countryPreference: String) {
        this.countryPreference = countryPreference
    }

    fun changeDefaultLanguage(language: Language) {
        setCustomDefaultLanguage(language)
    }

    fun setTypeFace(typeFace: Typeface) {
        try {
            textView_selectedCountry!!.setTypeface(typeFace)
//            setDialogTypeFace(typeFace)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun setDialogTypeFace(typeFace: Typeface, style: Int) {
//        try {
//            var style: Int = style
//            dialogTypeFace = typeFace
//            if (dialogTypeFace == null) {
//                style = DEFAULT_UNSET
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun setTypeFace(typeFace: Typeface, style: Int) {
//        try {
//            textView_selectedCountry!!.setTypeface(typeFace, style)
//            setDialogTypeFace(typeFace, style)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    fun setOnCountryChangeListener(onCountryChangeListener: OnCountryChangeListener) {
        this.onCountryChangeListener = onCountryChangeListener
    }

    fun setFlagSize(flagSize: Int) {
        imageViewFlag!!.getLayoutParams().height = flagSize
        imageViewFlag!!.requestLayout()
    }

    fun showFlag(showFlag: Boolean) {
        this.showFlag = showFlag
        refreshFlagVisibility()
        if (!isInEditMode())
            selectedCountry = selectedCCPCountry!!
    }

    private fun refreshFlagVisibility() {
        if (showFlag) {
            if (ccpUseEmoji) {
                linearFlagHolder!!.setVisibility(GONE)
            } else {
                linearFlagHolder!!.setVisibility(VISIBLE)
            }
        } else {
            linearFlagHolder!!.setVisibility(GONE)
        }
    }

    fun useFlagEmoji(useFlagEmoji: Boolean) {
        this.ccpUseEmoji = useFlagEmoji
        refreshFlagVisibility()
        selectedCountry = selectedCCPCountry!!
    }

    fun showFullName(showFullName: Boolean) {
        this.showFullName = showFullName
        selectedCountry = selectedCCPCountry!!
    }

    fun setPhoneNumberValidityChangeListener(phoneNumberValidityChangeListener: PhoneNumberValidityChangeListener) {
        this.phoneNumberValidityChangeListener = phoneNumberValidityChangeListener
        if (editText_registeredCarrierNumber != null) {
            reportedValidity = isValidFullNumber
            phoneNumberValidityChangeListener.onValidityChanged(reportedValidity)
        }
    }

    fun setAutoDetectionFailureListener(failureListener: FailureListener) {
        this.failureListener = failureListener
    }

    fun setCustomDialogTextProvider(customDialogTextProvider: CustomDialogTextProvider) {
        this.customDialogTextProvider = customDialogTextProvider
    }

    @JvmOverloads
    fun launchCountrySelectionDialog(countryNameCode: String? = null) {
        CountryCodeDialog.openCountryCodeDialog(this!!.codePicker!!, countryNameCode)
    }

    private fun getPhoneUtil(): PhoneNumberUtil {
        if (phoneUtil == null) {
            phoneUtil = PhoneNumberUtil.createInstance(context)
        }
        return this!!.phoneUtil!!
    }

    fun setAutoDetectedCountry(loadDefaultWhenFails: Boolean) {
        try {
            var successfullyDetected = false
            for (i in 0 until selectedAutoDetectionPref.representation.length) {
                when (selectedAutoDetectionPref.representation.get(i)) {
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

    fun detectSIMCountry(loadDefaultWhenFails: Boolean): Boolean {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountryISO = telephonyManager.getSimCountryIso()
            if (simCountryISO == null || simCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry()
                }
                return false
            }
            selectedCountry =
                CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                    getContext(),
                    getLanguageToApply(),
                    simCountryISO
                )!!
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
            return false
        }
    }

    fun detectNetworkCountry(loadDefaultWhenFails: Boolean): Boolean {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkCountryISO = telephonyManager.getNetworkCountryIso()
            if (networkCountryISO == null || networkCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry()
                }
                return false
            }
            selectedCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                getContext(),
                getLanguageToApply(),
                networkCountryISO
            )!!
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
            return false
        }
    }

    fun detectLocaleCountry(loadDefaultWhenFails: Boolean): Boolean {
        try {
            val localeCountryISO = context.getResources().getConfiguration().locale.getCountry()
            if (localeCountryISO == null || localeCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry()
                }
                return false
            }
            selectedCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(
                getContext(),
                getLanguageToApply(),
                localeCountryISO
            )!!
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (loadDefaultWhenFails) {
                resetToDefaultCountry()
            }
            return false
        }
    }

    fun setCountryAutoDetectionPref(selectedAutoDetectionPref: AutoDetectionPref) {
        this.selectedAutoDetectionPref = selectedAutoDetectionPref
    }


    fun onUserTappedCountry(CCPCountry: CCPCountry) {
        if (codePicker!!.rememberLastSelection) {
            codePicker!!.storeSelectedCountryNameCode(CCPCountry.nameCode)
        }
        setSelectedCountry(CCPCountry)
    }



//    fun onUserTappedCountry(CCPCountry: CCPCountry) {
//        if (codePicker!!.rememberLastSelection) {
//            codePicker!!.storeSelectedCountryNameCode(CCPCountry.nameCode)
//        }
////        selectedCountry = CCPCountry
////
////    }
//
//
//
//    return selectedCCPCountry
//}


    private fun getDefaultCountry(): CCPCountry? {
        return defaultCCPCountry
    }

    private fun setDefaultCountry(defaultCCPCountry: CCPCountry) {
        this.defaultCCPCountry = defaultCCPCountry
        //        Log.d(TAG, "Setting default country:" + defaultCountry.logString());
    }


private fun getSelectedCountry(): CCPCountry? {
    if (selectedCCPCountry == null) {
        setSelectedCountry(getDefaultCountry())
    }
    return selectedCCPCountry
}
internal fun setSelectedCountry(selectedCCPCountry: CCPCountry?) {
    var selectedCCPCountry = selectedCCPCountry

    //force disable area code country detection
    countryDetectionBasedOnAreaAllowed = false
    lastCheckedAreaCode = ""

    //as soon as country is selected, textView should be updated
    if (selectedCCPCountry == null) {
        selectedCCPCountry =
            CCPCountry.getCountryForCode(getContext(), getLanguageToApply(),
                this!!.preferredCountries!!, defaultCountryCode)
        if (selectedCCPCountry == null) {
            return
        }
    }

    this.selectedCCPCountry = selectedCCPCountry

    var displayText = ""

    // add flag if required
    if (showFlag && ccpUseEmoji) {
        if (isInEditMode()) {
            //                android studio preview shows huge space if 0 width space is not added.
            if (ccpUseDummyEmojiForPreview) {
                //show chequered flag if dummy preview is expected.
                displayText += "\uD83C\uDFC1\u200B "
            } else {
                displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "\u200B "
            }

        } else {
            displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "  "
        }
    }

    // add full name to if required
    if (showFullName) {
        displayText = displayText + selectedCCPCountry.name
    }

    // adds name code if required
    if (showNameCode) {
        if (showFullName) {
            displayText += " (" + selectedCCPCountry.name.toUpperCase() + ")"
        } else {
            displayText += " " + selectedCCPCountry.name.toUpperCase()
        }
    }

    // hide phone code if required
    if (showPhoneCode) {
        if (displayText.length > 0) {
            displayText += "  "
        }
        displayText += "+" + selectedCCPCountry.phoneCode
    }

    textView_selectedCountry!!.setText(displayText)

    //avoid blank state of ccp
    if (showFlag == false && displayText.length == 0) {
        displayText += "+" + selectedCCPCountry.phoneCode
        textView_selectedCountry!!.setText(displayText)
    }

    if (onCountryChangeListener != null) {
        onCountryChangeListener!!.onCountrySelected()
    }

    imageViewFlag!!.setImageResource(selectedCCPCountry.flagID)
    //        Log.d(TAG, "Setting selected country:" + selectedCountry.logString());

    updateFormattingTextWatcher()

    updateHint()

    //notify to registered validity listener
    if (editText_registeredCarrierNumber != null && phoneNumberValidityChangeListener != null) {
        reportedValidity = isValidFullNumber
        phoneNumberValidityChangeListener!!.onValidityChanged(reportedValidity)
    }

    //once updates are done, this will release lock
    countryDetectionBasedOnAreaAllowed = true

    //if the country was auto detected based on area code, this will correct the cursor position.
    if (countryChangedDueToAreaCode) {
        try {
            editText_registeredCarrierNumber!!.setSelection(lastCursorPosition)
            countryChangedDueToAreaCode = false
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //update country group
    updateCountryGroup()
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

    fun enableDialogInitialScrollToSelection(initialScrollToSelection: Boolean) {
        this.isDialogInitialScrollToSelectionEnabled = isDialogInitialScrollToSelectionEnabled
    }

    fun overrideClickListener(clickListener: View.OnClickListener) {
        customClickListener = clickListener
    }

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

        var code: String
        var country: String = ""
        var script: String = ""

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

    enum class AutoDetectionPref private constructor(representation: String) {
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

        var representation: String

        init {
            this.representation = representation
        }

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
    enum class TextGravity private constructor(i: Int) {
        LEFT(-1), CENTER(0), RIGHT(1);

        var enumIndex: Int = 0

        init {
            enumIndex = i
        }
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
        fun getCCPDialogTitle(language: Language, defaultTitle: String): String
        fun getCCPDialogSearchHintText(language: Language, defaultSearchHintText: String): String
        fun getCCPDialogNoResultACK(language: Language, defaultNoResultACK: String): String
    }

    protected override fun onDetachedFromWindow() {
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