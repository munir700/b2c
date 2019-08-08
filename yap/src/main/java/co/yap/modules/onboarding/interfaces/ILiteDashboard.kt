package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ILiteDashboard {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_LOGOUT_SUCCESS: Int
        get() = 1
        val EVENT_PRESS_COMPLETE_VERIFICATION: Int
            get() = 2

        val clickEvent: SingleClickEvent
        fun handlePressOnLogout()
        fun handlePressOnCompleteVerification()
        fun logout()
    }

    interface State : IBase.State {
        var name: String
        var email: String
    }
}