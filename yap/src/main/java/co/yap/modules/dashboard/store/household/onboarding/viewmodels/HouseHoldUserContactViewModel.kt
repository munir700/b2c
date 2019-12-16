package co.yap.modules.dashboard.store.household.onboarding.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserContact
import co.yap.modules.dashboard.store.household.onboarding.states.HouseHoldUserContactState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class HouseHoldUserContactViewModel(application: Application) :
    BaseOnboardingViewModel<IHouseHoldUserContact.State>(application),
    IHouseHoldUserContact.ViewModel/*,
    IRepositoryHolder<CardsRepository>*/ {

    override val state: HouseHoldUserContactState = HouseHoldUserContactState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAdd(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton() {
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_yap_house_hold_user_info_display_text_title))

    }

}
