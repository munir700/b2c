package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCreatePassCode
import co.yap.household.onboarding.onboarding.states.HouseHoldCreatePassCodeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class HouseHoldCreatePassCodeViewModel(application: Application) :
    BaseViewModel<IHouseHoldCreatePassCode.State>(application),
    IHouseHoldCreatePassCode.ViewModel {
    override val state: HouseHoldCreatePassCodeState = HouseHoldCreatePassCodeState()

    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnCreatePasscodeButton(id: Int) {
        nextButtonPressEvent.setValue(id)
    }
}