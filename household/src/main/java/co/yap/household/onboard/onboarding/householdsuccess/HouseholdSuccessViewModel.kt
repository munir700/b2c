package co.yap.household.onboard.onboarding.householdsuccess

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class HouseholdSuccessViewModel(application: Application) :
    BaseViewModel<IHouseHoldSuccess.State>(application = application), IHouseHoldSuccess.ViewModel{
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state = HouseHoldSuccessState(application)


}