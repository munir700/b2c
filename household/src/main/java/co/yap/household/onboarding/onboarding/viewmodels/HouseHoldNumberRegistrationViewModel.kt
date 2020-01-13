package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import android.graphics.Color
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboarding.onboarding.states.HouseHoldNumberRegistrationState
import co.yap.household.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent

class HouseHoldNumberRegistrationViewModel(application: Application) :
    OnboardingChildViewModel<IHouseHoldNumberRegistration.State>(application),
    IHouseHoldNumberRegistration.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: HouseHoldNumberRegistrationState = HouseHoldNumberRegistrationState()
    override var clickEvent: SingleClickEvent? = SingleClickEvent()

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

    override fun verifyHouseholdParentMobile() {
        launch {
            state.loading = true
            when (val response = repository.verifyHouseholdParentMobile(state.phoneNumber)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false

                }
            }
        }
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