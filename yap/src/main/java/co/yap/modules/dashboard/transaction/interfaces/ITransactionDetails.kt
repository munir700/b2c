package co.yap.modules.dashboard.transaction.interfaces

import co.yap.yapcore.IBase

interface ITransactionDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}