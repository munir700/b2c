package co.yap.household.dashboard.subaccounts

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class HouseHoldAccountsParentViewModel(application: Application) :
    BaseViewModel<IHouseHoldAccountsParent.State>(application), IHouseHoldAccountsParent.ViewModel {
    override val state: HouseHoldAccountsParentState = HouseHoldAccountsParentState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

}