package co.yap.app.modules.login.states

import androidx.databinding.Bindable
import co.yap.app.BR
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.yapcore.BaseState

class SystemPermissionState : BaseState(), ISystemPermission.State {

    @get:Bindable
    override var icon: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.icon)

        }

    override var title: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    override var termsAndConditionsVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.termsAndConditionsVisibility)
        }

    @get:Bindable
    override var buttonTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }

}
