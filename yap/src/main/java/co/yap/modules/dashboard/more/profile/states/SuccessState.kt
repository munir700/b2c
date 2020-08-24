package co.yap.modules.dashboard.more.profile.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.more.profile.intefaces.ISuccess
import co.yap.translation.Strings
import co.yap.yapcore.BaseState

class SuccessState : BaseState(), ISuccess.State {
    @get:Bindable
    override var topMainHeading: String = Strings.screen_email_address_success_display_text_title
        set(value) {
            field = value
            notifyPropertyChanged(BR.topMainHeading)
        }
    @get:Bindable
    override var staticString: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.staticString)
        }
    @get:Bindable
    override var destination: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.destination)
        }

    @get:Bindable
    override var buttonTitle: String = "Done"
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }

}