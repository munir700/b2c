package co.yap.household.onboard.onboarding.existinghousehold

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class ExistingHouseholdViewModel(application: Application) :
    BaseViewModel<IExistingHouseHold.State>(application = application), IExistingHouseHold.ViewModel{
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state = ExistingHouseHoldState(application)


}