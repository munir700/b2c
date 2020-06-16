package co.yap.modules.dashboard.store.household.success

import co.yap.yapcore.IBase

interface IHHAddUserSuccess {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>
    interface View : IBase.View<ViewModel>
}
