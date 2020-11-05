package co.yap.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class AlphaNumericEditText : AppCompatEditText {

    private var backupString = ""

    constructor(context: Context) : super(context) {
        initTextWatchers()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initTextWatchers()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initTextWatchers()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateValue(text.toString())
    }

    private fun updateValue(text: String) {
        setText(text)
    }

    private fun initTextWatchers() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                this@AlphaNumericEditText.removeTextChangedListener(this)
                val orignalString = charSequence.toString()
                if (orignalString.isNotEmpty() && Character.isWhitespace(charSequence[0])) {
                    if (text?.length ?: 0 > 1) {
                        setText(backupString)
                    } else
                        setText("")
                } else {
                    backupString = orignalString
                }

                this@AlphaNumericEditText.addTextChangedListener(this)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

}