package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel>{
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_SET_CARD_PIN: Int get() = 1
        val EVENT_SET_COMPLETE_VEERIFICATION: Int get() = 1
        val clickEvent: SingleClickEvent
        fun getDebitCards()
        val transactionLogicHelper: TransactionLogicHelper
    }

    interface State : IBase.State
}