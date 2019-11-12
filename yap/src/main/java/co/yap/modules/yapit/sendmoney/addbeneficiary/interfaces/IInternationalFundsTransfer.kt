package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalFundsTransfer {

    interface State : IBase.State {
        var senderCurrency: String
        var beneficiaryCurrency: String
        var beneficiaryCountry: String
        var senderAmount: String
        var beneficiaryAmount: String
        var valid: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
    }

    interface View : IBase.View<ViewModel>
}