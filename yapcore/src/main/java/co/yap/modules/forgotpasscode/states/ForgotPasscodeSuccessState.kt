package co.yap.modules.forgotpasscode.states

import androidx.databinding.Bindable
import co.yap.yapcore.BR
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeSuccess
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