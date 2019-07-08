package co.yap.app.modules.login.states

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import co.yap.app.BR
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.yapcore.BaseState

class VerifyPasscodeState : BaseState(), IVerifyPasscode.State {

    @get:Bindable
    override var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)

        }

    @get:Bindable
    override var dialerError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dialerError)
        }
    @get:Bindable
    override var passcode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.passcode)

        }
    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)

        }

    override fun validationPasscode(passcodeText: String) {

        if (passcodeText.equals("1234")) {
            passcode = "length can not b in sequence"
        }
    }

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

    fun validate(text: String) {
        if (text.length in 7 downTo 4) {
            // dialerError = "Lenght is less than 4"
            validationPasscode(text)
            valid = true

        } else {
            dialerError = ""
            valid = false

        }
    }

    override fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
//                    val text = arrayOf(.toString())toString
//                val text = p0.toString().takeLast(1)
//                array.add(text.toInt())
//                text.plus(p0.toString())

                /*   if (array.size in 7 downTo 5) {
                       for (i in 0 until array.count()) {

                           if (array[i + 1] - array[i] != 1) {
                               sequence = true
                               passcode = "length can not b in sequence"
                           }

                           if (array[i] != array[i + 1]) {
                               similar = true
                               passcode = "length can b in sequence"
                           }

                       }
                   }*/


//                if () {
//
//                }

                /* if (p0.toString() == "1234") {
                    passcode = "length can not b in sequence"
                } else {
                    passcode = ""
                }*/

            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validate(p0.toString())
            }
        }
    }

}