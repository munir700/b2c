package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalTransactionConfirmation {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun setData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnButtonClick(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var name: String?
        var picture: String?
        var position: Int?
        var flagLayoutVisibility: Boolean?
        var transferDescription: CharSequence?
        var referenceNumber: String?
        var confirmHeading: String?
        var receivingAmountDescription: CharSequence?
        var transferFeeDescription: CharSequence?
        var beneficiaryCountry: String?
    }
}