package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val transactionLogicHelper: TransactionLogicHelper
    }

    interface State : IBase.State
}