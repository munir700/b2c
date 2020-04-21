package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import co.yap.yapcore.R
import java.text.DecimalFormat
import java.util.*

class CurrencyEditText(
    context: Context,
    attrs: AttributeSet
) : AppCompatEditText(context, attrs) {
    private var currencyLocale: Locale? = null
    var defaultLocale = Locale.US
    private var allowNegativeValues = false
    var rawValue = 0L
        protected set
    private var textWatcher: CurrencyTextWatcher? = null
    private var hintCache: String? = null
    private var decimalDigits = 0
    private var mValueInLowestDenom = 0
    var cursor = 0

    fun setAllowNegativeValues(negativeValuesAllowed: Boolean) {
        allowNegativeValues = negativeValuesAllowed
    }

    fun areNegativeValuesAllowed(): Boolean {
        return allowNegativeValues
    }

    fun setValue(value: Long) {
        val formattedText = format(value)
        setText(formattedText)
    }

    var locale: Locale?
        get() = currencyLocale
        set(locale) {
            currencyLocale = locale
            refreshView()
        }

    private val hintString: String?
        get() {
            val result = super.getHint() ?: return null
            return super.getHint().toString()
        }

    fun getDecimalDigits(): Int {
        return decimalDigits
    }

    private fun setDecimalDigits(digits: Int) {
        require(!(digits < 0 || digits > 27)) { "Decimal Digit value must be between 0 and 27" }
        decimalDigits = digits
        refreshView()
    }

    fun configureViewForLocale(locale: Locale?) {
        currencyLocale = locale
        val currentCurrency = getCurrencyForLocale(locale)
        decimalDigits = currentCurrency.defaultFractionDigits
        refreshView()
    }

    fun formatCurrency(value: String): String {
        return format(value)
    }

    fun formatCurrency(rawVal: Long): String {
        return format(rawVal)
    }

    private fun refreshView() {
        setText(format(rawValue))
        updateHint()
    }

    private fun format(value: Long): String {
        return formatText(
            value.toString(),
            currencyLocale,
            defaultLocale,
            decimalDigits
        )
    }

    private fun format(value: String): String {
        return formatText(
            value,
            currencyLocale,
            defaultLocale,
            decimalDigits
        )
    }

    private fun init() {
        setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_COMMA) {
            } else if (keyCode == KeyEvent.KEYCODE_NUMPAD_DOT) {
            }
            false
        }
        this.gravity = Gravity.RIGHT
        this.inputType = InputType.TYPE_CLASS_TEXT
        var filters =
            arrayOfNulls<InputFilter>(1)
        filters[0] = InputFilter.LengthFilter(20)
        filters = filters
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                changeDecimalKeyboard()
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
        currencyLocale = retrieveLocale()
        val currentCurrency = getCurrencyForLocale(currencyLocale)
        decimalDigits = currentCurrency.defaultFractionDigits
        initCurrencyTextWatcher()
    }

    protected fun setValueInLowestDenom(mValueInLowestDenom: Int) {
        this.mValueInLowestDenom = mValueInLowestDenom
    }

    private fun changeDecimalKeyboard() {
        setRawInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED)
    }

    private fun changeSignedKeyboard() {
        setRawInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED)
    }

    private fun initCurrencyTextWatcher() {
        if (textWatcher != null) {
            removeTextChangedListener(textWatcher)
        }
        textWatcher = CurrencyTextWatcher(this)
        addTextChangedListener(textWatcher)
    }

    private fun processAttributes(
        context: Context,
        attrs: AttributeSet
    ) {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText)
        hintCache = hintString
        updateHint()
        setAllowNegativeValues(
            array.getBoolean(
                R.styleable.CurrencyEditText_allow_negative_values,
                false
            )
        )
        setDecimalDigits(
            array.getInteger(
                R.styleable.CurrencyEditText_decimal_digits,
                decimalDigits
            )
        )
        array.recycle()
    }

    private fun updateHint() {
        if (hintCache == null) {
            hint = defaultHintValue
        }
    }

    private val defaultHintValue: String
        private get() = try {
            Currency.getInstance(currencyLocale).symbol
        } catch (e: Exception) {
            Log.w(
                "CurrencyEditText",
                String.format(
                    "An error occurred while getting currency symbol for hint using locale '%s', falling back to defaultLocale",
                    currencyLocale
                )
            )
            try {
                Currency.getInstance(defaultLocale).symbol
            } catch (e1: Exception) {
                Log.w(
                    "CurrencyEditText",
                    String.format(
                        "An error occurred while getting currency symbol for hint using default locale '%s', falling back to USD",
                        defaultLocale
                    )
                )
                Currency.getInstance(Locale.US).symbol
            }
        }

    @SuppressLint("LogNotTimber")
    private fun retrieveLocale(): Locale {
        val locale: Locale
        locale = try {
            resources.configuration.locale
        } catch (e: Exception) {
            Log.w(
                "CurrencyEditText",
                String.format(
                    "An error occurred while retrieving users device locale, using fallback locale '%s'",
                    defaultLocale
                ),
                e
            )
            defaultLocale
        }
        return locale
    }

    @SuppressLint("LogNotTimber")
    private fun getCurrencyForLocale(locale: Locale?): Currency {
        val currency: Currency
        currency = try {
            Currency.getInstance(locale)
        } catch (e: Exception) {
            try {
                Log.w(
                    "CurrencyEditText",
                    String.format(
                        "Error occurred while retrieving currency information for locale '%s'. Trying default locale '%s'...",
                        currencyLocale,
                        defaultLocale
                    )
                )
                Currency.getInstance(defaultLocale)
            } catch (e1: Exception) {
                Log.e(
                    "CurrencyEditText",
                    "Both device and configured default locales failed to report currentCurrency data. Defaulting to USD."
                )
                Currency.getInstance(Locale.US)
            }
        }
        return currency
    }

    @SuppressLint("LogNotTimber")
    fun formatText(
        value: String,
        locale: Locale?,
        defaultLocale: Locale,
        decimalDigits: Int
    ): String {
        var value = value
        if (value == "-") return value
        val currencyDecimalDigits: Int
        currencyDecimalDigits = if (decimalDigits != null) {
            decimalDigits
        } else {
            val currency = Currency.getInstance(locale)
            try {
                currency.defaultFractionDigits
            } catch (e: Exception) {
                Log.e(
                    "CurrencyTextFormatter",
                    "Illegal argument detected for currency: $currency, using currency from defaultLocale: $defaultLocale"
                )
                Currency.getInstance(defaultLocale).defaultFractionDigits
            }
        }
        val currencyFormatter: DecimalFormat = try {
            DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
        } catch (e: Exception) {
            try {
                Log.e(
                    "CurrencyTextFormatter",
                    "Error detected for locale: $locale, falling back to default value: $defaultLocale"
                )
                DecimalFormat.getCurrencyInstance(defaultLocale) as DecimalFormat
            } catch (e1: Exception) {
                Log.e(
                    "CurrencyTextFormatter",
                    "Error detected for defaultLocale: $defaultLocale, falling back to USD."
                )
                DecimalFormat.getCurrencyInstance(Locale.US) as DecimalFormat
            }
        }
        var isNegative = false
        if (value.contains("-")) {
            isNegative = true
        }
        value = value.replace("[^\\d]".toRegex(), "")
        if (value != "") {
            if (value.length <= currencyDecimalDigits) {
                val formatString = "%" + currencyDecimalDigits + "s"
                value = String.format(formatString, value).replace(' ', '0')
            }
            val preparedVal =
                StringBuilder(value).insert(value.length - currencyDecimalDigits, '.')
                    .toString()
            //Convert the string into a double, which will be passed into the currency formatter
            var newTextValue = preparedVal.toDouble()
            newTextValue *= if (isNegative) -1 else 1
            currencyFormatter.minimumFractionDigits = currencyDecimalDigits
            value = currencyFormatter.format(newTextValue)
        } else {
            throw IllegalArgumentException("Invalid amount of digits found (either zero or too many) in argument val")
        }
        return value
    }

    internal inner class CurrencyTextWatcher(private val editText: CurrencyEditText) :
        TextWatcher {
        private var ignoreIteration = false
        private var lastGoodInput = ""
        var cursorPosition = 0
        var okcommo = false
        var clickDot = false
        var isEmpty = false
        var clickDelete = false
        var rightPost = false
        var currentTextsize = 0
        override fun afterTextChanged(editable: Editable) { //Use the ignoreIteration flag to stop our edits to the text field from triggering an endlessly recursive call to afterTextChanged
            if (!ignoreIteration) {
                ignoreIteration = true
                //Start by converting the editable to something easier to work with, then remove all non-digit characters
                var newText = editable.toString()
                var textToDisplay: String
                if (newText.isEmpty()) {
                    lastGoodInput = ""
                    editText.rawValue = 0
                    editText.setText("")
                    return
                }
                if (clickDelete && okcommo && editable.toString().length - 2 <= editText.selectionStart) {
                    rightPost = true
                    if (editText.selectionStart == editable.toString().length - 1) {
                        newText =
                            newText.substring(0, newText.length - 1) + "0" + newText.substring(
                                newText.length - 1,
                                newText.length
                            )
                    } else if (editText.selectionStart == editable.toString().length) {
                        newText += "0"
                    }
                } else {
                    rightPost = false
                }
                if (!clickDelete && editText.selectionStart - 1 >= 0) {
                    val word = newText.substring(
                        editText.selectionStart - 1,
                        editText.selectionStart
                    )
                    if (word.contentEquals(".") || word.contentEquals(",")) {
                        okcommo = true
                        clickDot = true
                    } else {
                        okcommo = false
                        clickDot = false
                    }
                } else {
                    okcommo = false
                    clickDot = false
                }
                newText = if (editText.areNegativeValuesAllowed()) newText.replace(
                    "[^0-9/-]".toRegex(),
                    ""
                ) else newText.replace("[^0-9]".toRegex(), "")
                if (newText != "" && newText != "-") { //Store a copy of the raw input to be retrieved later by getRawValue
                    editText.rawValue = java.lang.Long.valueOf(newText)
                }
                //ondalik bolumdesin
                if (!clickDelete && !okcommo && editable.toString().length - 2 <= editText.selectionStart) {
                    newText = newText.substring(0, newText.length - 1)
                    rightPost = true
                } else {
                    rightPost = false
                }
                try {
                    textToDisplay = formatText(
                        newText,
                        editText.locale,
                        editText.defaultLocale,
                        editText.getDecimalDigits()
                    )
                    textToDisplay = textToDisplay.substring(1)
                } catch (exception: IllegalArgumentException) {
                    textToDisplay = lastGoodInput
                }
                editText.setText(textToDisplay)
                //Store the last known good input so if there are any issues with new input later, we can fall back gracefully.
                lastGoodInput = textToDisplay
                if (!clickDelete && rightPost && cursorPosition <= editable.toString().length - 2) {
                    if (cursorPosition + 2 <= lastGoodInput.length) {
                        editText.setSelection(cursorPosition + 1)
                    } else {
                        editText.setSelection(lastGoodInput.length)
                    }
                    rightPost = false
                } else if (!clickDelete && rightPost && cursorPosition == editable.toString().length) {
                    editText.setSelection(lastGoodInput.length)
                    rightPost = false
                } else {
                    if (isEmpty) {
                        editText.setSelection(1)
                        cursorPosition = 1
                        isEmpty = false
                    } else if (okcommo) {
                        if (cursorPosition != lastGoodInput.length) {
                            if (clickDot) {
                                editText.setSelection(editText.length() - 2)
                                clickDot = false
                            } else {
                                editText.setSelection(cursorPosition + 1)
                            }
                        } else {
                            editText.setSelection(textToDisplay.length - 1)
                        }
                    } else {
                        okcommo = false
                        val diff = Math.abs(currentTextsize - lastGoodInput.length)
                        if (clickDelete && !rightPost) {
                            if (diff == 2) {
                                if (cursorPosition == 0) {
                                    editText.setSelection(0)
                                } else {
                                    editText.setSelection(cursorPosition - 1)
                                }
                            } else if (diff > 2) {
                                editText.setSelection(0)
                            } else {
                                editText.setSelection(cursorPosition)
                            }
                            clickDelete = false
                        } else if (clickDelete && rightPost) {
                            if (cursorPosition + 1 <= lastGoodInput.length) {
                                editText.setSelection(cursorPosition - 1)
                            } else {
                                editText.setSelection(lastGoodInput.length - 3)
                            }
                            clickDelete = false
                        } else {
                            if (cursorPosition + Math.abs(currentTextsize - lastGoodInput.length) > lastGoodInput.length) {
                                editText.setSelection(editText.selectionStart + 1)
                            } else {
                                editText.setSelection(
                                    cursorPosition + Math.abs(
                                        currentTextsize - lastGoodInput.length
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                ignoreIteration = false
                if (isEmpty && editable.toString().isEmpty()) {
                    var tempText: String? = null
                    try {
                        tempText = formatText(
                            "000",
                            editText.locale,
                            editText.defaultLocale,
                            editText.getDecimalDigits()
                        )
                        tempText = tempText.substring(1)
                    } catch (exception: IllegalArgumentException) {
                        tempText = ""
                    }
                    editText.setText(tempText)
                    editText.setSelection(1)
                    cursorPosition = 1
                    isEmpty = false
                }
            }
        }

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            if (s.isEmpty()) {
                isEmpty = true
            }
            if (start != 0) {
                currentTextsize = s.toString().length
                cursorPosition = start
                okcommo = !(editText.text!!.length - 3 >= cursorPosition && editText.text!!.length - 3 > 0)
            } else if (start == 0 && !ignoreIteration) {
                currentTextsize = s.toString().length
                cursorPosition = start
                okcommo = !(editText.text!!.length - 3 >= cursorPosition && editText.text!!.length - 3 > 0)
            }
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        private fun changeDecimalKeyboard() {
            editText.setRawInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED)
        }

        private fun changeSignedKeyboard() {
            editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        }

        init {
            editText.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    clickDelete = true
                } else if (keyCode == 55) {
                    clickDelete = false
                    okcommo = true
                    clickDot = true
                    cursorPosition = editText.text!!.length - 2
                    changeSignedKeyboard()
                } else if (keyCode == 56) {
                    clickDelete = false
                    okcommo = true
                    clickDot = true
                    cursorPosition = editText.text!!.length - 2
                    changeSignedKeyboard()
                } else {
                    clickDelete = false
                }
                false
            }
        }
    }


    init {
        init()
        processAttributes(context, attrs)
    }
}