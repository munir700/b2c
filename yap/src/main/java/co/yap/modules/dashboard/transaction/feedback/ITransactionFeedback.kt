package co.yap.modules.dashboard.transaction.feedback

import co.yap.modules.dashboard.transaction.feedback.adaptor.TransactionFeedbackAdapter
import co.yap.yapcore.IBase

interface ITransactionFeedback {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        val adapter: TransactionFeedbackAdapter
    }

    interface State : IBase.State
}