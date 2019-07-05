package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IName
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.BaseState

class NameState : BaseState(), IName.State {

    override var dummyStrings: Array<String> = arrayOf("0123")

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            notifyPropertyChanged(BR.valid)
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
}