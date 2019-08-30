package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.models.TransactionAdapterModel
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
        fun getGraphDummyData(): ArrayList<co.yap.modules.onboarding.models.TransactionModel>
        fun loadJSONDummyList(): ArrayList<TransactionAdapterModel>
        fun getDebitCards()
    }

    interface State : IBase.State
}