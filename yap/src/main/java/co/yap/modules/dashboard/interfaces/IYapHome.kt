package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.helpers.transaction.TransactionsViewHelper
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel> {
        var transactionViewHelper: TransactionsViewHelper
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val transactionLogicHelper: TransactionLogicHelper
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State
}