package com.yap.uikit.widgets.edittexts

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.yap.uikit.R
import com.yap.uikit.extensions.isEmpty

@Keep
class CollapsedColoredHintTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {
    private var hintTextColorNormal = 0
    private var hintTextColorSelected = 0
    private var drawablePadding: Int = -1

    init {
        init()
        attrs?.let { initializeCustomAttrs(context, it) }

    }

    fun init() {
        this.post {
            initializeTextWatcher()
        }
    }

    private fun initializeCustomAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable
                .CollapsedColoredHintTextInputLayout, 0, 0
        )
        try {
            drawablePadding = typedArray.getDimensionPixelOffset(
                R.styleable.CollapsedColoredHintTextInputLayout_android_drawablePadding, -1
            )
            hintTextColorNormal = typedArray.getColor(
                R.styleable.CollapsedColoredHintTextInputLayout_hintTextColorNormal,
                ContextCompat.getColor(getContext(), R.color.black)
            )
            hintTextColorSelected = typedArray.getColor(
                R.styleable.CollapsedColoredHintTextInputLayout_hintTextColorSelected,
                ContextCompat.getColor(getContext(), R.color.grey_dark)
            )

        } finally {
            typedArray.recycle()
        }
    }

    private fun initializeTextWatcher() {
        editText?.let {
            defaultHintTextColor =
                if (isEmpty(it)) ColorStateList.valueOf(hintTextColorNormal) else ColorStateList.valueOf(
                    hintTextColorSelected
                )
        }
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                defaultHintTextColor =
                    if (isEmpty(s)) ColorStateList.valueOf(hintTextColorNormal) else ColorStateList.valueOf(
                        hintTextColorSelected
                    )
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }
}