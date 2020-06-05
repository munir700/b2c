package co.yap.household.onboard.onboarding.householdsuccess

import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldSuccess {

    interface View: IBase.View<ViewModel> {

    }

    interface ViewModel: IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun setUserData(accountInfo: AccountInfo)
    }

    interface State: IBase.State {
        var name: String?
    }
}