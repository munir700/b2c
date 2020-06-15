package co.yap.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.isEmpty
import com.google.android.material.textfield.TextInputLayout
import java.lang.reflect.Method

@Keep
class CollapsedColoredHintTextInputLayout : TextInputLayout {
    private var collapseHintMethod: Method? = null
    private var hintTextColorNormal = 0
    private var hintTextColorSelected = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        initializeCustomAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        initializeCustomAttrs(context, attrs)
    }

    fun init() {
        isHintAnimationEnabled = false
        try {
            this.post {
                initializeTextWatcher()
            }
            collapseHintMethod = TextInputLayout::class.java.getDeclaredMethod(
                "collapseHint",
                Boolean::class.javaPrimitiveType
            )
            collapseHintMethod?.isAccessible = true
        } catch (ignored: Exception) {
        }
    }

    private fun initializeCustomAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable
                .CollapsedColoredHintTextInputLayout, 0, 0
        )
        try {
            typedArray.getColor(
                R.styleable.CollapsedColoredHintTextInputLayout_hintTextColorNormal,
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark)
                    .also { if (it != 0) hintTextColorNormal = it }
            )
            typedArray.getColor(
                R.styleable.CollapsedColoredHintTextInputLayout_hintTextColorSelected,
                ContextCompat.getColor(getContext(), R.color.greyDark)
                    .also { if (it != 0) hintTextColorSelected = it }
            )
        } finally {
            typedArray.recycle()
        }
    }

    override fun invalidate() {
        super.invalidate()
        try {
            collapseHintMethod?.invoke(this, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initializeTextWatcher() {
        editText?.let {
            hintTextColor =
                if (isEmpty(it)) ColorStateList.valueOf(hintTextColorNormal) else ColorStateList.valueOf(
                    hintTextColorSelected
                )
        }
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hintTextColor =
                    if (isEmpty(s)) ColorStateList.valueOf(hintTextColorNormal) else ColorStateList.valueOf(
                        hintTextColorSelected
                    )
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}
