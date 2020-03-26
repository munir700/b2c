package co.yap.household.onboard.onboarding.existinghousehold

import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IExistingHouseHold {

    interface View: IBase.View<ViewModel> {

    }

    interface ViewModel: IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var subAccountInvitationStatus: SingleLiveEvent<String>
        fun handlePressOnView(id: Int)
        fun setUserData(user: AccountInfo)
        fun subAccountInvitationStatus(notificationStatus: String)
    }

    interface State: IBase.State {
        var firstName: String?
        var lastName: String?
    }
}