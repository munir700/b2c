package co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces

 import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddSpareCard{
    interface State : IBase.State {
        var cardType: String
     }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var cardType: String
        fun handlePressOnAddVirtualCardSuccess(id: Int)
        fun handlePressOnAddPhysicalCardSuccess(id: Int)
        fun handlePressOnConfirmPhysicalCardPurchase(id: Int)
    }

    interface View : IBase.View<ViewModel>
}