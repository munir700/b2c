package co.yap.modules.dashboard.transaction.category

import co.yap.yapcore.IBase

interface ITransactionCategory {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}