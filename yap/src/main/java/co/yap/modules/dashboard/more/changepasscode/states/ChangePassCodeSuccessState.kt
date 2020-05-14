package co.yap.modules.dashboard.more.changepasscode.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.more.changepasscode.interfaces.IChangePassCodeSuccess
import co.yap.modules.dashboard.more.profile.intefaces.ISuccess
import co.yap.translation.Strings
import co.yap.yapcore.BaseState

class ChangePassCodeSuccessState : BaseState(), IChangePassCodeSuccess.State {
    @get:Bindable
    override var topMainHeading: String = Strings.screen_email_address_success_display_text_heading
        set(value) {
            field = value
            notifyPropertyChanged(BR.topMainHeading)
        }
    @get:Bindable
    override var topSubHeading: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.topSubHeading)
        }

}