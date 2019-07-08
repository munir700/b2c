package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IName
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.StringUtils

class NameState : BaseState(), IName.State {

    override var dummyStrings: Array<String> = arrayOf("0123")



    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            notifyPropertyChanged(BR.valid)
            setFirstNameTextWatcher(firstName)
        }

    @get:Bindable
    override var firstNameError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstNameError)
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            notifyPropertyChanged(BR.valid)
            setLastNameTextWatcher(lastName)
        }

    @get:Bindable
    override var lastNameError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastNameError)
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var valid: Boolean = false
        get() = validate()

    private fun validate(): Boolean {
        return StringUtils.validateName(firstName) && StringUtils.validateName(lastName) && firstNameError.isNullOrEmpty() && lastNameError.isNullOrEmpty()
    }


    fun setFirstNameTextWatcher(value: String) {

        if (!value.isNullOrEmpty() && value.length >= 3) {

            if (StringUtils.validateName(value)) {
                //set tick tru
//                remov error
                firstNameError = firstName
                notifyPropertyChanged(BR.firstNameError)
            } else {
                //set tick faalse err
//                set error
                firstNameError = firstName
                notifyPropertyChanged(BR.firstNameError)

            }
        }
    }

    fun setLastNameTextWatcher(value: String) {

        if (!value.isNullOrEmpty() && value.length >= 3) {

            if (StringUtils.validateName(value)) {
                //set tick tru
//                remov error
                lastNameError = lastName
                notifyPropertyChanged(BR.lastNameError)
            } else {

                //set tick faalse err
//                set error
                lastNameError = lastName
                notifyPropertyChanged(BR.lastNameError)

            }
        }
    }
}