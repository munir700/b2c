package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.states.CreatePasscodeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class CreatePasscodeViewModel(application: Application) : BaseViewModel<ICreatePasscode.State>(application),
    ICreatePasscode.ViewModel {

    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnCreatePasscodeButton() {
        nextButtonPressEvent.value = true
    }

    override val state: CreatePasscodeState = CreatePasscodeState()

}