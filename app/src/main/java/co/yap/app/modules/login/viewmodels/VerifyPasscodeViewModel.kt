package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.states.VerifyPasscodeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class VerifyPasscodeViewModel(application: Application) : BaseViewModel<IVerifyPasscode.State>(application),
    IVerifyPasscode.ViewModel {

    override val state: VerifyPasscodeState = VerifyPasscodeState()
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnSignInButton() {
//        state.dialerError=state.passcode
        signInButtonPressEvent.postValue(true)
    }
}