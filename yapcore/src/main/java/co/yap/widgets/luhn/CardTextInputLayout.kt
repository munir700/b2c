package co.yap.widgets.luhn

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import co.yap.widgets.CollapsedColoredHintTextInputLayout
import co.yap.yapcore.R

class CardTextInputLayout : CollapsedColoredHintTextInputLayout {
    var hasValidInput: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    var onCEndIconOnClickListener: ((view: View, text: String) -> Unit)? =
        null

    private fun init() {
        editText?.inputType = EditorInfo.TYPE_CLASS_NUMBER
        endIconDrawable = ContextCompat.getDrawable(
            context,
            R.drawable.ic_camera
        )
        setEndIconOnClickListener {
            if (endIconMode == END_ICON_CLEAR_TEXT) editText?.setText("")
            onCEndIconOnClickListener?.invoke(it, editText?.text.toString())
        }
        editText?.doOnTextChanged { text, start, before, count ->
            passwordVisibilityToggleRequested(null)
        }
        editText?.setOnFocusChangeListener { v, hasFocus ->

        }
    }

    fun passwordVisibilityToggleRequested(@DrawableRes drawResId: Int? = null) {

        // Store the current cursor position
        if (isEndIconVisible) {
            val selection = editText?.selectionEnd
            endIconDrawable = if (editText?.text.isNullOrEmpty()) {
                endIconMode = END_ICON_CUSTOM
                drawResId?.let { ContextCompat.getDrawable(context, it) }
                    ?: ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_camera
                    )
            } else {
                endIconMode = END_ICON_CLEAR_TEXT
                drawResId?.let { ContextCompat.getDrawable(context, it) }
                    ?: ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_close_dark
                    )
            }
        }
    }

    fun hasValidInput() = hasValidInput


}