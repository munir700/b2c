package co.yap.modules.dashboard.store.cardplans.interfaces

import co.yap.yapcore.IBase

interface ICardPlans {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}