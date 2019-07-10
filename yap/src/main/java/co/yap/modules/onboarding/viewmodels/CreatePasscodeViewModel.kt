package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.states.CreatePasscodeState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.StringUtils

class CreatePasscodeViewModel(application: Application) : BaseViewModel<ICreatePasscode.State>(application),
    ICreatePasscode.ViewModel {

    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun handlePressOnCreatePasscodeButton() {
        if (validateAggressively()) {
            nextButtonPressEvent.value = true
        }
    }

    private fun validateAggressively(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.passcode)
        val isSequenced = StringUtils.isSequenced(state.passcode)
        if (!isSame) state.dialerError = getString(Strings.screen_create_passcode_display_text_error_sequence)
        if (!isSequenced) state.dialerError = getString(Strings.screen_create_passcode_display_text_error_same_digits)
        return !isSame && !isSequenced
    }

    override val state: CreatePasscodeState = CreatePasscodeState()

}