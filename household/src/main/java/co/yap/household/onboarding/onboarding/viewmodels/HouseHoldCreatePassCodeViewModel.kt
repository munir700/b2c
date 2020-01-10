package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCreatePassCode
import co.yap.household.onboarding.onboarding.states.HouseHoldCreatePassCodeState
import co.yap.household.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.StringUtils

open class HouseHoldCreatePassCodeViewModel(application: Application) :
    OnboardingChildViewModel<IHouseHoldCreatePassCode.State>(application),
    IHouseHoldCreatePassCode.ViewModel {
    override val state: HouseHoldCreatePassCodeState = HouseHoldCreatePassCodeState()

    override val clickEvent: SingleClickEvent? = SingleClickEvent()

    override fun handlePressOnCreatePasscodeButton(id: Int) {
        if (validateAggressively()){
            clickEvent?.setValue(id)
        }
    }
    override fun onResume() {
        super.onResume()
        setProgress(50)
    }

    private fun validateAggressively(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.passcode)
        val isSequenced = StringUtils.isSequenced(state.passcode)
        if (isSequenced) state.dialerError =
            getString(Strings.screen_create_passcode_display_text_error_sequence)
        if (isSame) state.dialerError =
            getString(Strings.screen_create_passcode_display_text_error_same_digits)
        return !isSame && !isSequenced
    }
}