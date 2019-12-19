package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase

interface IInternationalTransactionConfirmation {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        fun handlePressOnButtonClick(id:Int)
    }
    interface State : IBase.State {
        var name: String?
        var picture: String?
        var position: Int?
        var flagLayoutVisibility: Boolean?
        var transferDescription: String?
        var referenceNumber: String?
    }
}