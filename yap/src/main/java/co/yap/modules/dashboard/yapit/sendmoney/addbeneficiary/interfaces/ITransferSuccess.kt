package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransferSuccess {
    interface View : IBase.View<ViewModel> {
        fun setObservers()

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleClickEvent
        var clickEvent: SingleClickEvent
        var updatedCardBalanceEvent:SingleClickEvent
        fun handlePressOnButtonClick(id: Int)
        fun getAccountBalanceRequest()
    }

    interface State : IBase.State {
        var transferType: String?
        var successHeader: String?
        var amount: String?
        var currency: String?
        var flag: String?
        var name: String?
        var picture: String
        var pickUpAgentLocationAddress: String?
        var referenceNumber: String?
        var flagLayoutVisibility: Boolean?
        var position: Int?
        var locationLayoutVisibility: Boolean
        var beneficiaryCountry: String?
        var transferAmountHeading: String?
        var buttonTitle: String?
        var availableBalanceString: CharSequence?
        var cutOffTimeMsg: ObservableField<String>

    }
}