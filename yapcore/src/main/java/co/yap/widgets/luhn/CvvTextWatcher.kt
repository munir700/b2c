package co.yap.widgets.luhn

import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener

abstract class CvvTextWatcher(cardTextInputLayout: CardTextInputLayout) : TextWatcher {
    private var mCardTextInputLayout: CardTextInputLayout? = cardTextInputLayout

    init {
        mCardTextInputLayout?.editText?.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (mCardTextInputLayout!!.hasValidInput || mCardTextInputLayout!!.editText!!
                        .text.toString().isEmpty()
                ) {
                    mCardTextInputLayout?.error = ""
                } else {
                    mCardTextInputLayout?.error = "Enter a valid card code"
                }
            }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(source: CharSequence?, start: Int, before: Int, count: Int) {
        mCardTextInputLayout?.error = ""
        val moveToNext = isValid(source!!)
        mCardTextInputLayout?.passwordVisibilityToggleRequested()
        mCardTextInputLayout?.hasValidInput = moveToNext
        val text = mCardTextInputLayout?.editText?.text.toString()
        onValidated(moveToNext, text)
    }

    open fun isValid(source: CharSequence): Boolean {
        return source.toString().length == 3
    }

    protected abstract fun onValidated(moveToNext: Boolean, pin: String?)
}