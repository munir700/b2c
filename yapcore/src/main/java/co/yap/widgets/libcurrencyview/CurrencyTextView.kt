package co.yap.widgets.libcurrencyview

import android.content.Context
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.text.style.StrikethroughSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.parseToInt
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

class CurrencyTextView : AppCompatTextView {
    private var currencySymbol: String? = null
    private var formatter: String? = "#,###,###,###,###,###,###"
    private var textContent: CharSequence? = null
    private var prefixText: CharSequence? = null
    private var suffixText: CharSequence? = null
    private var strikeThrough = false
    private var nullToZero = false
    private var symbolSize = 0f
    private var decimalSize = 0f
    private var prefixSuffixSize = 0f
    private var _showCurrency = true
    private val _showCommas = false

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        getAttrs(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        getAttrs(context, attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        text = textContent
    }

    fun setText(
        text: CharSequence?,
        prefixText: CharSequence?,
        suffixText: CharSequence?
    ) {
        this.prefixText = prefixText
        this.suffixText = suffixText
        setText(text)
    }

    override fun setText(
        text: CharSequence,
        type: BufferType
    ) {
        textContent = text
        super.setText(formatCurrencyString(text), type)
    }

    fun setPrefixText(prefixText: CharSequence?) {
        this.prefixText = prefixText
        text = textContent
    }

    fun setSuffixText(suffixText: CharSequence?) {
        this.suffixText = suffixText
        text = textContent
    }

    val valueString: String
        get() = textContent.toString()

    fun clearPrefixText() {
        setPrefixText(null)
    }

    fun clearSuffixText() {
        setSuffixText(null)
    }

    fun setSymbol(symbolString: CharSequence?) {
        currencySymbol = symbolString.toString()
    }

    private fun getAttrs(
        context: Context,
        attrs: AttributeSet
    ) {
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.CurrencyTextView)
        strikeThrough =
            ta.getBoolean(R.styleable.CurrencyTextView_ctv_strikeThrough, false)
        nullToZero = ta.getBoolean(R.styleable.CurrencyTextView_ctv_nullToZero, true)
        _showCurrency = ta.getBoolean(R.styleable.CurrencyTextView_ctv_showCurrency, true)
        symbolSize = ta.getDimensionPixelSize(
            R.styleable.CurrencyTextView_ctv_currencySymbolSize,
            textSize.toInt()
        ).toFloat()
        decimalSize = ta.getDimensionPixelSize(
            R.styleable.CurrencyTextView_ctv_decimalTextSize,
            textSize.toInt()
        ).toFloat()
        prefixSuffixSize = ta.getDimensionPixelSize(
            R.styleable.CurrencyTextView_ctv_prefixSuffixTextSize,
            textSize.toInt()
        ).toFloat()
        val customSymbol =
            ta.getString(R.styleable.CurrencyTextView_ctv_currencySymbol)
        if (ta.hasValueOrEmpty(R.styleable.CurrencyTextView_ctv_formatter))
            formatter = ta.getString(R.styleable.CurrencyTextView_ctv_formatter)
        currencySymbol = if (TextUtils.isEmpty(customSymbol)) "AED " else customSymbol
        ta.recycle()
    }

    private fun formatCurrencyString(oriPrice: CharSequence): Spanny {
        var oriPrice: CharSequence? = oriPrice
        val oriPriceChars: Spanny
        val spanny = Spanny()
            .append(prefixText, AbsoluteSizeSpan(prefixSuffixSize.toInt(), false))
        if ("" == oriPrice || null == oriPrice) {
            oriPrice = if (nullToZero) {
                "00"
            } else {
                spanny.append(
                    suffixText,
                    AbsoluteSizeSpan(prefixSuffixSize.toInt(), false)
                )
                return spanny
            }
        }
        oriPriceChars = setUpNumberStyle(oriPrice)
        if (_showCurrency)
            spanny.append(currencySymbol, AbsoluteSizeSpan(symbolSize.toInt(), false))
        spanny.append(
                    oriPriceChars,
                    if (strikeThrough) StrikethroughSpan() else null
                )
                .append(suffixText, AbsoluteSizeSpan(prefixSuffixSize.toInt(), false))
        return spanny
    }

    private fun setUpNumberStyle(oriPrice: CharSequence): Spanny {
        return if (decimalSize != textSize) {
            setUpDifDecimal(oriPrice)
        } else {
            decimalSize = textSize
            setUpDifDecimal(oriPrice)
//            Spanny(
//                getDecimalFormatted(oriPrice),
//                AbsoluteSizeSpan(textSize.toInt(), false)

//            )
        }
    }

    private fun getDecimalFormatted(oriPrice: CharSequence): CharSequence {
        val numberPattern = formatter
        val formatter =
            DecimalFormat.getInstance(Locale.getDefault()) as DecimalFormat
        formatter.applyPattern(numberPattern)
        return formatter.format(oriPrice)
    }

    private fun setUpDifDecimal(amount: CharSequence): Spanny {
        val result = Spanny()
        try {
            val decimalScale = BigDecimal(100)
            val originD = BigDecimal(amount.toString())
            val integerD =
                originD.setScale(0, BigDecimal.ROUND_DOWN)
            val decimalD =
                originD.multiply(decimalScale).subtract(integerD.multiply(decimalScale))
            val numberPattern = formatter
            val formatter =
                DecimalFormat.getInstance(Locale.getDefault()) as DecimalFormat
            formatter.applyPattern(numberPattern)
            //CharSequence aa =  formatter.format(integerD.intValueExact());
            result.append(formatter.format(integerD.intValueExact().toLong()))
                // result.append(getDecimalFormatted(integerD.intValueExact().toLong()))
                .append(".")
                .setSpan(
                    AbsoluteSizeSpan(textSize.toInt(), false),
                    0,
                    result.length
                )

            var str = decimalD.toString()

            if (str.contains(".")) {
                val nums = str.split(".")
                str = nums[0]
            }


            if (str.length > 2) {
                str = str.substring(0, 2)
            }

            //else str = String.format("%02d", decimalD.intValueExact())
            // String.format("%2d", decimalD.intValueExact())
            if (str.parseToInt() in 0..9)
                str = "0$str"
            result.append(
                str,
                // BigDecimal(str).intValueExact().toString(),
                AbsoluteSizeSpan(decimalSize.toInt(), false)
            )
        } catch (e: Exception) {
            //    e.printStackTrace()
            result.clear()
            result.append(amount, AbsoluteSizeSpan(textSize.toInt(), false))
        }
        return result
    }

    internal class Spanny : SpannableStringBuilder {
        private var flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

        constructor() : super("") {}
        constructor(text: CharSequence?) : super(text) {}
        constructor(text: CharSequence?, vararg spans: Any?) : super(text) {
            for (span in spans) {
                setSpan(span, 0, length)
            }
        }

        constructor(text: CharSequence, span: Any?) : super(text) {
            setSpan(span, 0, text.length)
        }

        fun append(text: CharSequence?, vararg spans: Any?): Spanny {
            if (null != text) {
                append(text)
                for (span in spans) {
                    setSpan(span, length - text.length, length)
                }
            }
            return this
        }

        fun append(text: CharSequence?, span: Any?): Spanny {
            if (null != text) {
                append(text)
                setSpan(span, length - text.length, length)
            }
            return this
        }

        /**
         * 添加图片
         * @return this `Spanny`.
         */
        fun append(text: CharSequence, imageSpan: ImageSpan?): Spanny {
            var text = text
            text = ".$text"
            append(text)
            setSpan(imageSpan, length - text.length, length - text.length + 1)
            return this
        }

        /**
         * 添加一个纯文本
         * @return this `Spanny`.
         */
        override fun append(text: CharSequence?): Spanny {
            text?.let { super.append(text) }
            return this
        }

        /**
         * Change the flag. Default is SPAN_EXCLUSIVE_EXCLUSIVE.
         * The flags determine how the span will behave when text is
         * inserted at the start or end of the span's range
         * @param flag see [Spanned].
         */
        fun setFlag(flag: Int) {
            this.flag = flag
        }

        /**
         * Mark the specified range of text with the specified object.
         * The flags determine how the span will behave when text is
         * inserted at the start or end of the span's range.
         */
        fun setSpan(span: Any?, start: Int, end: Int) {
            span?.let { setSpan(it, start, end, flag) }
        }

        /**
         * Sets a span object to all appearances of specified text in the spannable.
         * A new instance of a span object must be provided for each iteration
         * because it can't be reused.
         *
         * @param textToSpan Case-sensitive text to span in the current spannable.
         * @param getSpan    Interface to get a span for each spanned string.
         * @return `Spanny`.
         */
        fun findAndSpan(textToSpan: CharSequence, getSpan: GetSpan): Spanny {
            var lastIndex = 0
            while (lastIndex != -1) {
                lastIndex = toString().indexOf(textToSpan.toString(), lastIndex)
                if (lastIndex != -1) {
                    setSpan(getSpan.span, lastIndex, lastIndex + textToSpan.length)
                    lastIndex += textToSpan.length
                }
            }
            return this
        }

        /**
         * Interface to return a new span object when spanning multiple parts in the text.
         */
        interface GetSpan {
            /**
             * @return A new span object should be returned.
             */
            val span: Any?
        }

        companion object {
            /**
             * Sets span objects to the text. This is more efficient than creating a new instance of Spanny
             * or SpannableStringBuilder.
             * @return `SpannableString`.
             */
            fun spanText(
                text: CharSequence,
                vararg spans: Any?
            ): SpannableString {
                val spannableString = SpannableString(text)
                for (span in spans) {
                    spannableString.setSpan(
                        span,
                        0,
                        text.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                return spannableString
            }

            fun spanText(text: CharSequence, span: Any?): SpannableString {
                val spannableString = SpannableString(text)
                spannableString.setSpan(
                    span,
                    0,
                    text.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                return spannableString
            }
        }
    }
}