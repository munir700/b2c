package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransferSuccess {

    interface State : IBase.State {
        var pickUpAgentLocation: String?
        var transferType: String?
        var amount: String?
        var currency: String?
        var flag: String?
        var name: String?
        var picture: String?
        var pickUpAgentLocationAddress: String?
        var referenceNumber: String?
        var flagLayoutVisibility: Boolean?

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleClickEvent
        var clickEvent: SingleClickEvent
        fun handlePressOnGoBackToDashboard(id: Int)
    }

    interface View : IBase.View<ViewModel>

}