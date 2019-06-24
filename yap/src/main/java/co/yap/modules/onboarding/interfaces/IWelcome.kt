package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface IWelcome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnGetStarted()
    }
    interface State : IBase.State
}