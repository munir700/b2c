package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import android.graphics.Color
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboarding.onboarding.states.HouseHoldNumberRegistrationState
import co.yap.household.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class HouseHoldNumberRegistrationViewModel(application: Application) :
    OnboardingChildViewModel<IHouseHoldNumberRegistration.State>(application),
    IHouseHoldNumberRegistration.ViewModel {
    override val state: HouseHoldNumberRegistrationState = HouseHoldNumberRegistrationState()
    override var clickEvent: SingleClickEvent?= SingleClickEvent()

    override fun onCreate() {
        populateState()
        super.onCreate()
        state.existingYapUser = parentViewModel?.state?.existingYapUser

    }
    override fun onResume() {
        super.onResume()
        updateBackground(Color.WHITE)
        setProgress(20)
    }
    override fun populateState() {
//        state.parentName = "Sufyan"
//        state.welcomeHeading =
//            getString(Strings.screen_house_hold_number_registration_display_text_heading).format(
//                state.parentName
//            )
//        state.numberConfirmationValue =
//            getString(Strings.screen_house_hold_number_registration_display_text_parent_description)
    }

    override fun handlePressOnConfirm(id: Int) {
        clickEvent?.setValue(id)
    }
}