package co.yap.modules.dashboard.cards.addpaymentcard.spare.main.interfaces

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardLogicHelper
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddSpareCard {
    interface State : IBase.State {
        var cardType: String
        var cardName: String
        var virtualCardFee: String
        var coreButtonText: String
        var availableBalance: ObservableField<CharSequence>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val addSpareVirtualCardLogicHelper: AddSpareVirtualCardLogicHelper
        var paymentCard: Card?
        val CONFIRM_VIRTUAL_PURCHASE: Int
        val ADD_VIRTUAL_SPARE_SUCCESS_EVENT: Int
        val list: MutableList<CoreBottomSheetData>
        fun handlePressOnView(id: Int)
        fun requestAddSpareVirtualCard()
        fun isEnoughBalance(): Boolean
        fun setListData()
    }

    interface View : IBase.View<ViewModel>
}