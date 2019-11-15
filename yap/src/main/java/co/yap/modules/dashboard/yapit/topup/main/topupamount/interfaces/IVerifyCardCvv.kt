package co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces

import co.yap.networking.customers.responsedtos.beneficiary.TopUpTransactionModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IVerifyCardCvv {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun buttonClickEvent(id: Int)
        val clickEvent: SingleClickEvent
        fun topUpTransactionRequest(model: TopUpTransactionModel?)
    }

    interface State : IBase.State{
        var cardCvv:String
    }
}