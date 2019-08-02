package co.yap.modules.onboarding.states

import android.text.Editable
import android.text.TextWatcher
import androidx.core.text.toSpannable
import androidx.databinding.Bindable
import co.yap.BR

import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.StringUtils
import java.lang.StringBuilder

open class CreatePasscodeState : BaseState(), ICreatePasscode.State {

    @get:Bindable
    override var sequence: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.sequence)
        }
    @get:Bindable
    override var similar: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.similar)
        }


    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)

        }

    @get:Bindable
    override var passcode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.passcode)

        }
    @get:Bindable
    override var dialerError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dialerError)
        }


    fun validate() {
        if (passcode.length in 7 downTo 4) {
            valid = true
        } else {
            dialerError = ""
            valid = false
        }
    }

    override fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passcode = p0.toString()
                validate()
            }
        }
    }
}

