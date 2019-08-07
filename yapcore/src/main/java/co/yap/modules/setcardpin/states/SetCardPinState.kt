package co.yap.modules.setcardpin.states

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState


class SetCardPinState : BaseState(), ISetCardPin.State {

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
    override var pincode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pincode)

        }
    @get:Bindable
    override var dialerError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dialerError)
        }


    fun validate() {
        if (pincode.length == 4) {
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
                pincode = p0.toString()
                validate()
            }
        }
    }
}

