package co.yap.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.modules.forgotpasscode.interfaces.ICreatePasscode
import co.yap.modules.forgotpasscode.states.CreatePasscodeState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.StringUtils

open class CreatePasscodeViewModel(application: Application) :
    BaseViewModel<ICreatePasscode.State>(application),
    ICreatePasscode.ViewModel {
    override var mobileNumber: String = ""

    override val state: CreatePasscodeState = CreatePasscodeState()
    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnCreatePasscodeButton(id: Int) {
        if (id == R.id.tvTermsAndConditions) {
            nextButtonPressEvent.setValue(id)
        } else {
            if (validateAggressively()) {
                nextButtonPressEvent.setValue(id)
            }
        }
    }

    protected fun validateAggressively(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.passcode)
        val isSequenced = StringUtils.isSequenced(state.passcode)
        if (isSequenced) state.dialerError =
            getString(Strings.screen_create_passcode_display_text_error_sequence)
        if (isSame) state.dialerError =
            getString(Strings.screen_create_passcode_display_text_error_same_digits)
        return !isSame && !isSequenced
    }
}