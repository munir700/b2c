package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.models.TransactionModel
import co.yap.modules.dashboard.models.TransactionResponseDTO
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapDashboardHome {

    interface View : IBase.View<ViewModel>{
    }


    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun loadJSONDummyList(): ArrayList<TransactionModel>
//        fun loadJSONDummyList():TransactionResponseDTO

    }

}