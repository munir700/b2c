package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.models.TransactionAdapterModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun loadJSONDummyList(): ArrayList<TransactionAdapterModel>
    }

    interface State : IBase.State
}