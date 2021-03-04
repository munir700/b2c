package co.yap.modules.dashboard.store.cardplans.interfaces

import co.yap.yapcore.IBase

interface IMainCardPlans {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}