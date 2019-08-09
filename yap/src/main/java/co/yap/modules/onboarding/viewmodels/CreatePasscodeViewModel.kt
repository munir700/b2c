package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.states.CreatePasscodeState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.StringUtils

open class CreatePasscodeViewModel(application: Application) : BaseViewModel<ICreatePasscode.State>(application),
    ICreatePasscode.ViewModel {
    override var mobileNumber: String=""

    override val state: CreatePasscodeState = CreatePasscodeState()
    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnCreatePasscodeButton(id:Int) {
        if (validateAggressively()) {
            nextButtonPressEvent.setValue(id)
        }
    }

    protected fun validateAggressively(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.passcode)
        val isSequenced = StringUtils.isSequenced(state.passcode)
        if (isSequenced) state.dialerError = getString(Strings.screen_create_passcode_display_text_error_sequence)
        if (isSame) state.dialerError = getString(Strings.screen_create_passcode_display_text_error_same_digits)
        return !isSame && !isSequenced
    }
}