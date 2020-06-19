package co.yap.modules.dashboard.store.household.onboarding.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldSuccess
import co.yap.modules.dashboard.store.household.onboarding.states.HouseHoldSuccessState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent

@Deprecated("")
class HouseHoldSuccessViewModel(application: Application) :
    BaseOnboardingViewModel<IHouseHoldSuccess.State>(application),
    IHouseHoldSuccess.ViewModel/*,
    IRepositoryHolder<CardsRepository>*/ {
    override fun handlePressOnShare(id: Int) {
        trackEvent(HHSubscriptionEvents.HH_SHARE.type)
        clickEvent.setValue(id)
    }

    override fun handlePressOnGoBackToDashBoard(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: HouseHoldSuccessState = HouseHoldSuccessState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_yap_house_hold_user_info_display_text_title))
        toggleToolBarVisibility(false)
        state.houseHoldUserName = parentViewModel?.firstName ?: ""
        state.houseHoldDescription =
            getString(Strings.screen_yap_house_hold_success_display_text_direct_message).format(
                state.houseHoldUserName
            )
        state.houseHoldUserMobile = parentViewModel?.userMobileNo?.replace(" ", "") ?: ""
        state.houseHoldUserPassCode = parentViewModel?.tempPasscode ?: "0000"
    }
}
