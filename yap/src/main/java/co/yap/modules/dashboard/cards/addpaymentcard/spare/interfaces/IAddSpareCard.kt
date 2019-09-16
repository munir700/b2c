package co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces

 import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
 import co.yap.yapcore.SingleLiveEvent

interface IAddSpareCard{
    interface State : IBase.State {
        var cardType: String
     }

    interface ViewModel : IBase.ViewModel<State> {
//        val clickEvent: SingleClickEvent
        var cardType: String
        fun handlePressOnAddVirtualCardSuccess()
        fun handlePressOnAddPhysicalCardSuccess()
        fun handlePressOnConfirmVirtualCardPurchase()
        fun handlePressOnConfirmPhysicalCardPurchase()
        val clickEvent: SingleLiveEvent<Boolean>

    }

    interface View : IBase.View<ViewModel>
}