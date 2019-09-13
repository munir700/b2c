package co.yap.modules.dashboard.cards.addpaymentcard.interfaces

import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISpareCards {

    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun loadJSONDummyList(): ArrayList<BenefitsModel>
        fun handlePressOnAddVirtualCard(id: Int)
        fun handlePressOnAddPhysicalCard(id: Int)

    }

    interface View : IBase.View<ViewModel>
}