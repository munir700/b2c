package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface ILiteDashboard {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val logoutSuccess: SingleLiveEvent<Boolean>
        fun handlePressOnLogout()
    }

    interface State : IBase.State {
        var name: String
        var email: String
    }
}