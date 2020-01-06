package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboarding.onboarding.states.HouseHoldNumberRegistrationState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class HouseHoldNumberRegistrationViewModel(application: Application) :
    BaseViewModel<IHouseHoldNumberRegistration.State>(application),
    IHouseHoldNumberRegistration.ViewModel {
    override val state: HouseHoldNumberRegistrationState = HouseHoldNumberRegistrationState()
    override var clickEvent: SingleClickEvent?= SingleClickEvent()

    override fun onCreate() {
        populateState()
        super.onCreate()
    }

    override fun populateState() {
        state.parentName = "Sufyan"
        state.welcomeHeading =
            getString(Strings.screen_house_hold_number_registration_display_text_heading).format(
                state.parentName
            )
        state.numberConfirmationValue =
            getString(Strings.screen_house_hold_number_registration_display_text_parent_description)
    }

    override fun handlePressOnConfirm(id: Int) {
        clickEvent?.setValue(id)
    }
}