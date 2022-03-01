package com.yap.uikit.widgets.dialerpad

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import com.google.android.material.checkbox.MaterialCheckBox
import com.yap.uikit.R

class SecureCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val DEFAULT_CODE_MIN_LENGTH = 4
    private val DEFAULT_CODE_MAX_LENGTH = 6
    var mCodeViews: MutableList<CheckBox> = mutableListOf()
    private var mCode = ""

    //private val mCodeLength = DEFAULT_CODE_LENGTH
    private var mCodeMinLength: Int = DEFAULT_CODE_MIN_LENGTH
    private var mCodeMaxLength: Int = DEFAULT_CODE_MAX_LENGTH
    private var mListener: OnSecureCodeListener? = null
    private var customHeight: Int = 0
    private var margin: Int = 0

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SecureCodeView)
        customHeight = typedArray.getDimensionPixelSize(
            R.styleable.SecureCodeView_scv_size,
            resources.getDimensionPixelSize(R.dimen._14sdp)
        )
        margin = typedArray.getDimensionPixelSize(
            R.styleable.SecureCodeView_scv_margin,
            resources.getDimensionPixelSize(R.dimen.ui_kit_small_margin)
        )
        mCodeMaxLength = typedArray.getInt(
            R.styleable.SecureCodeView_scv_maxLength,
            DEFAULT_CODE_MAX_LENGTH
        )
        mCodeMinLength = typedArray.getInt(
            R.styleable.SecureCodeView_scv_minLength,
            DEFAULT_CODE_MIN_LENGTH
        )
        typedArray.recycle()
        gravity = Gravity.CENTER_HORIZONTAL
        setUpCodeViews()
        orientation = HORIZONTAL
    }

    private fun setUpCodeViews() {
        removeAllViews()
        mCodeViews.clear()
        mCode = ""

        for (i in 0 until mCodeMaxLength) {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.layout_passcode_view, null) as MaterialCheckBox
            val layoutParams = LayoutParams(
                customHeight, customHeight
            )
            layoutParams.gravity = Gravity.CENTER
            layoutParams.setMargins(margin, margin, margin, margin)
            view.layoutParams = layoutParams
            view.isChecked = false
            view.visibility = GONE
            view.gravity = Gravity.CENTER
            addView(view)
            mCodeViews.add(view)
        }
        mListener?.onCodeCompleted("", false)
    }

    fun input(number: String): Int {
        if (mCode.length == mCodeMaxLength) {
            return mCode.length
        }
        mCodeViews[mCode.length].toggle() //.setChecked(true);
        mCodeViews[mCode.length].visibility = VISIBLE
        mCode += number
        mListener?.onCodeChange(mCode)
        if (mCode.length >= mCodeMinLength) {
            mListener?.onCodeCompleted(mCode, true)
        }
        return mCode.length
    }

    fun delete(): Int {
        if (mCode.isEmpty()) {
            mListener?.onCodeCompleted("", false)
            return mCode.length
        }
        mCode = mCode.substring(0, mCode.length - 1)
        mListener?.onCodeChange(mCode)
        mCodeViews[mCode.length].toggle() //.setChecked(false);
        mCodeViews[mCode.length].visibility = GONE
        if (mCode.length < mCodeMinLength)
            mListener?.onCodeCompleted(mCode, false)
        return mCode.length
    }

    fun clearCode() {
        mCode = ""
        for (codeView in mCodeViews) {
            codeView.isChecked = false
            codeView.visibility = GONE
        }
        mListener?.onCodeCompleted(mCode, false)
    }

    fun getInputCodeLength(): Int {
        return mCode.length
    }

    fun setListener(listener: OnSecureCodeListener) {
        mListener = listener
    }

    fun getCode() = mCode

}