package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.yapcore.BaseState

class MobileState : BaseState(), IMobile.State {

    @get:Bindable
    override var mobile: String = ""
        get() = TODO("not implemented")
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)
            valid = validate()

        }

    override var mobileError: String
        get() = TODO("not implemented")
        set(value) {}

    override var valid: Boolean
        get() = TODO("not implemented")
        set(value) {}

    fun validate(): Boolean {
        return (mobile.length > 9)
    }
}