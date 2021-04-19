package co.yap.modules.dashboard.transaction.feedback

import co.yap.yapcore.IBase

interface ITransactionFeedback {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}