package co.yap.widgets.edittext

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import co.yap.widgets.MaskTextWatcher
import co.yap.yapcore.R
import co.yap.yapcore.helpers.Utils.getDefaultCountryCode
import co.yap.yapcore.helpers.getCountryCodeForRegion
import com.google.android.material.textfield.TextInputEditText
import com.google.i18n.phonenumbers.PhoneNumberUtil

class PhoneTextInputEditText : TextInputEditText {
    private var phoneUtil: PhoneNumberUtil? = PhoneNumberUtil.getInstance()
    private var showHint: Boolean = false
    var maskTextWatcher: MaskTextWatcher? = null
    private var mPrefix: String? = null

    var prefix: String?
        get() = this.mPrefix
        set(prefix) {
            this.mPrefix = prefix
            if (mPrefix?.isNotBlank()!!)
                mask(mPrefix)
            invalidate()
        }
    var mask: String? = null
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                removeTextChangedListener(maskTextWatcher)
            } else {
                removeTextChangedListener(maskTextWatcher)
                maskTextWatcher = MaskTextWatcher(this, mask!!)
                addTextChangedListener(maskTextWatcher)
            }
        }
    val rawText: String
        get() {
            val formatted = text
            return maskTextWatcher?.unformat(formatted) ?: formatted.toString()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val array = context.obtainStyledAttributes(it, R.styleable.PhoneTextInputEditText)
            showHint =
                array.getBoolean(R.styleable.PhoneTextInputEditText_p_showHint, false)
            array.recycle()
        }
    }

    private fun mask(code: String?) {
        code?.let {
            val countryCode =
                code.replace("+", "")
            var formattedNumber = ""

            val exampleNumber =
                phoneUtil?.getExampleNumberForType(
                    getCountryCodeForRegion(countryCode.toInt()),
                    PhoneNumberUtil.PhoneNumberType.MOBILE
                )

            exampleNumber?.let {
                formattedNumber = exampleNumber.nationalNumber.toString()
                formattedNumber = PhoneNumberUtils.formatNumber(
                    countryCode + formattedNumber,
                    getDefaultCountryCode(context)
                )
                if (formattedNumber != null) {
                    formattedNumber = formattedNumber.substring(countryCode.length).trim()
                }
                if (showHint) {
                    hint = formattedNumber
                }
                formattedNumber = formattedNumber.replace("[0-9]".toRegex(), "#")

            }
            mask = formattedNumber

            text = text
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["app:prefixText"], requireAll = false)
        fun setPhonePrefix(view: PhoneTextInputEditText, prefixText: String) {
            view.prefix = prefixText
        }

    }
}