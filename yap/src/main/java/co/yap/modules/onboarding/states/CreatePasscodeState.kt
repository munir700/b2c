package co.yap.modules.onboarding.states

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import co.yap.BR

import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.yapcore.BaseState

class CreatePasscodeState : BaseState(), ICreatePasscode.State {
    val list: ArrayList<Int> = ArrayList(6)
    override fun checkButtonValidation() {
        /*if(list.size>3){
            for(i in 0..list.size){
                if(list[i].plus(1).minus(list[i])==1){
                    sequence=true
                }else if(list[i]==list[i+1]){
                    similar=true
                }
            }
        }*/
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


    fun validate(text: String) {
        if (text.length in 7 downTo 4) {
            validationPasscode(text)
            valid = true

        } else {
            dialerError = ""
            valid = false

        }
    }

    override fun validationPasscode(passcodeText: String) {

        /* if (passcodeText.equals("1234")) {
             passcode = "length can not b in sequence"
         }*/
        /*if (sequence){
            dialerError="length can not be in sequence"
        }else if (similar){
            dialerError="Passcode can not be same digity"
        }*/
        passcode = passcodeText
    }


    override fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
             /*   if (list.size>0){
                    list.add(list[list.lastIndex])
                }
                else{
                    list.add(p0.toString().toInt())
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

