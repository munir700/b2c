package co.yap.modules.transaction_filters.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionFilters {
    interface View : IBase.View<ViewModel>
    interface ViewModel:IBase.ViewModel<State>{
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }
    interface State:IBase.State{

    }
}