package co.yap.modules.dashboard.cards.addpaymentcard.main.interfaces

import androidx.databinding.ObservableField
import co.yap.networking.cards.responsedtos.VirtualCardDesigns
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddPaymentCard {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnButton(id: Int)
        fun getVirtualCardDesigns(success: () -> Unit)
        val clickEvent: SingleClickEvent
        var physicalCardFee: String
        var virtualCardFee: String
        var virtualCardDesignsList: ArrayList<VirtualCardDesigns>
        var selectedVirtualCard: VirtualCardDesigns?
        var selectedCardName: ObservableField<String>
        var isFromBlockCard: ObservableField<Boolean>
        var selectedVirtualCardPosition: ObservableField<Int>
    }

    interface View : IBase.View<ViewModel>
}