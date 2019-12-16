package co.yap.modules.dashboard.store.household.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IHouseHoldUserInfo {

    interface State : IBase.State {
        var firstName: String
        var lastName: String
        var emailAddress: String
        var valid: Boolean
        val backButtonPressEvent: SingleLiveEvent<Boolean>

    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
        fun handlePressOnBackButton()


    }

    interface View : IBase.View<ViewModel>

}