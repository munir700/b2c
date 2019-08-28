package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.models.TransactionModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun loadJSONDummyList(): ArrayList<TransactionModel>
    }

    interface State : IBase.State
}