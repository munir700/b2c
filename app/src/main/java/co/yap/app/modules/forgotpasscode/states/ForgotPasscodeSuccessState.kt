package co.yap.app.modules.forgotpasscode.states

import androidx.databinding.Bindable
import co.yap.app.BR
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeSuccess
import co.yap.yapcore.BaseState

class ForgotPasscodeSuccessState : BaseState(), IForgotPasscodeSuccess.State {


    @get:Bindable
    override var subTitle: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.subTitle)
        }
    @get:Bindable
    override var buttonTitle: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.buttonTitle)
        }

}