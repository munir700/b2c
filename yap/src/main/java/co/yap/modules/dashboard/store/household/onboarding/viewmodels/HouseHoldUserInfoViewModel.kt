package co.yap.modules.dashboard.store.household.onboarding.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserInfo
import co.yap.modules.dashboard.store.household.onboarding.states.HouseHoldUserInfoStates
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class HouseHoldUserInfoViewModel(application: Application) :
    BaseOnboardingViewModel<IHouseHoldUserInfo.State>(application),
    IHouseHoldUserInfo.ViewModel/*,
    IRepositoryHolder<CardsRepository>*/ {

    override val state: HouseHoldUserInfoStates = HouseHoldUserInfoStates(application)

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton() {
    }

    override fun setUserName() {
        parentViewModel?.username = state.firstName + " " + state.lastName
        parentViewModel?.userMobileNo = state.emailAddress
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_yap_house_hold_user_info_display_text_title))
    }

}