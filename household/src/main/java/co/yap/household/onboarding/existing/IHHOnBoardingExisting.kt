package co.yap.household.onboarding.existing

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingExisting {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun subAccountInvitationStatus(
            notificationStatus: String,
            apiResponse: ((String?) -> Unit?)?
        )
    }

    interface State : IBase.State {
    }
}
