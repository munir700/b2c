package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface ILiteDashboard {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnLogout()
    }

    interface State : IBase.State {
        var name: String
        var email: String
    }
}