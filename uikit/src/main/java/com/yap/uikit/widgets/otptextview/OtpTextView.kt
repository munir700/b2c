package com.yap.uikit.widgets.otptextview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.yap.uikit.R
import com.yap.uikit.extensions.dip2px
import com.yap.uikit.extensions.getScreenWidth
import java.util.*
import java.util.regex.Pattern

private const val DEFAULT_LENGTH = 4
private const val DEFAULT_HEIGHT = 0
private const val DEFAULT_WIDTH = 0
private const val DEFAULT_SPACE = -1
private const val DEFAULT_SPACE_LEFT = 4
private const val DEFAULT_SPACE_RIGHT = 4
private const val DEFAULT_SPACE_TOP = 4
private const val DEFAULT_SPACE_BOTTOM = 4
private const val PATTERN = "[1234567890]*"

open class OtpTextView : FrameLayout {
    private var itemViews: MutableList<ItemView>? = null
    private var otpChildEditText: OTPChildEditText? = null
    var otpListener: OTPListener? = null

    private var length: Int = 0

    private val filter: InputFilter
        get() = InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                if (!Pattern.compile(
                        PATTERN
                    )
                        .matcher(source[i].toString())
                        .matches()
                ) {
                    return@InputFilter ""
                }
            }
            null
        }

    val otp: String?
        get() = otpChildEditText?.text?.toString()

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val styles = context.obtainStyledAttributes(attrs, R.styleable.OtpTextView)
        styleEditTexts(styles, attrs)
        styles.recycle()
    }

    private fun styleEditTexts(styles: TypedArray, attrs: AttributeSet?) {
        length = styles.getInt(R.styleable.OtpTextView_length, DEFAULT_LENGTH)
        generateViews(styles, attrs)
    }

    private fun generateViews(styles: TypedArray, attrs: AttributeSet?) {
        itemViews = ArrayList()
        if (length > 0) {

            val otp = styles.getString(R.styleable.OtpTextView_otp)
            val width = if (styles.hasValue(R.styleable.OtpTextView_custom_width)) {
                styles.getDimension(
                    R.styleable.OtpTextView_custom_width, context.dip2px(
                        DEFAULT_WIDTH
                    ).toFloat()
                ).toInt()
            } else {
                getScreenWidth() / (length + 2)
            }
            val height = if (styles.hasValue(R.styleable.OtpTextView_custom_height)) {
                styles.getDimension(
                    R.styleable.OtpTextView_custom_height,
                    context.dip2px(DEFAULT_HEIGHT).toFloat()
                ).toInt()
            } else {
                getScreenWidth() / (length + 2) + (getScreenWidth() / (length + 2)) / 4
            }


            val space = styles.getDimension(
                R.styleable.OtpTextView_box_margin,
                context.dip2px(DEFAULT_SPACE).toFloat()
            ).toInt()
            val spaceLeft = styles.getDimension(
                R.styleable.OtpTextView_box_margin_left,
                context.dip2px(DEFAULT_SPACE_LEFT).toFloat()
            ).toInt()
            val spaceRight = styles.getDimension(
                R.styleable.OtpTextView_box_margin_right,
                context.dip2px(DEFAULT_SPACE_RIGHT).toFloat()
            ).toInt()
            val spaceTop = styles.getDimension(
                R.styleable.OtpTextView_box_margin_top,
                context.dip2px(DEFAULT_SPACE_TOP).toFloat()
            ).toInt()
            val spaceBottom = styles.getDimension(
                R.styleable.OtpTextView_box_margin_bottom,
                context.dip2px(DEFAULT_SPACE_BOTTOM).toFloat()
            ).toInt()

            val params = LinearLayout.LayoutParams(width, height)

            if (space > 0) {
                params.setMargins(space, space, space, space)
            } else {
                params.setMargins(spaceLeft, spaceTop, spaceRight, spaceBottom)
            }

            val editTextLayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            editTextLayoutParams.gravity = Gravity.CENTER
            otpChildEditText = OTPChildEditText(context)
            otpChildEditText?.filters = arrayOf(filter, InputFilter.LengthFilter(length))
            setTextWatcher(otpChildEditText)
            addView(otpChildEditText, editTextLayoutParams)


            val linearLayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val linearLayout = LinearLayout(context)

            addView(linearLayout, linearLayoutParams)

            for (i in 0 until length) {
                val itemView = ItemView(context, attrs)
                itemView.setViewState(ItemView.INACTIVE)
                linearLayout.addView(itemView, i, params)
                itemViews?.add(itemView)
            }
            if (otp != null) {
                setOTP(otp)
            } else {
                setOTP("")
            }
        } else {
            throw IllegalStateException("Please specify the length of the otp view")
        }
    }

    private fun setTextWatcher(otpChildEditText: OTPChildEditText?) {
        otpChildEditText?.addTextChangedListener(object : TextWatcher {
            /**
             * @param s
             * @param start
             * @param count
             * @param after
             */
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            /**
             * @param s
             * @param start
             * @param before
             * @param count
             */
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                otpListener?.let { otpListener ->
                    otpListener.onInteractionListener()
                    if (s.length == length) {
                        otpListener.onOTPComplete(s.toString())
                    }
                }
                setOTP(s)
                setFocus(s.length)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun setFocus(length: Int) {
        itemViews?.let { itemViews ->
            for (i in itemViews.indices) {
                if (i == length) {
                    itemViews[i].setViewState(ItemView.ACTIVE)
                } else {
                    itemViews[i].setViewState(ItemView.INACTIVE)
                }
            }
            if (length == itemViews.size) {
                itemViews[itemViews.size - 1].setViewState(ItemView.ACTIVE)
            }
        }
    }

    fun setOTP(s: CharSequence) {
        itemViews?.let { itemViews ->
            for (i in itemViews.indices) {
                if (i < s.length) {
                    itemViews[i].setText(s[i].toString())
                } else {
                    itemViews[i].setText("")
                }
            }
        }
    }

    fun requestFocusOTP() {
        otpChildEditText?.requestFocus()
    }

    fun showError() {
        itemViews?.let { itemViews ->
            for (itemView in itemViews) {
                itemView.setViewState(ItemView.ERROR)
            }
        }
    }

    fun resetState() {
        otp?.let {
            setFocus(it.length)
        }
    }

    fun showSuccess() {
        itemViews?.let { itemViews ->
            for (itemView in itemViews) {
                itemView.setViewState(ItemView.SUCCESS)
            }
        }
    }

    fun setOTP(otp: String) {
        otpChildEditText?.setText(otp)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(l: OnTouchListener) {
        super.setOnTouchListener(l)
        otpChildEditText?.setOnTouchListener(l)
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["otpAttrChanged"])
        fun setOtpListener(view: OtpTextView, listener: InverseBindingListener?) {

            if (listener != null) {
                view.otpListener = object : OTPListener {
                    override fun onInteractionListener() {
                        // fired when user types something in the Otpbox
                        listener.onChange()
                    }

                    override fun onOTPComplete(otp: String) {
                        // fired when user has entered the OTP fully.

                    }
                }
            }
        }


        @JvmStatic
        @BindingAdapter("otpChangeListener")
        fun setOtpListener(view: OtpTextView, listener: OTPListener?) {
            if (listener != null) view.otpListener = listener
        }


        @JvmStatic
        @BindingAdapter("otp")
        fun setOtp(view: OtpTextView, value: String) {
            if (view.otp != value) {
                view.setOTP(value)
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "otp")
        fun getOtp(view: OtpTextView): String = view.otp!!
    }
}
