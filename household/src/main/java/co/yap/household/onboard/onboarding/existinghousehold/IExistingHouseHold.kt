package co.yap.household.onboard.onboarding.existinghousehold

import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IExistingHouseHold {

    interface View: IBase.View<ViewModel> {

    }

    interface ViewModel: IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun setUserData(user: AccountInfo)
    }

    interface State: IBase.State {
        var firstName: String?
        var lastName: String?
    }
}