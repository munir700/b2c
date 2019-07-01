package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface IOnboarding {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}