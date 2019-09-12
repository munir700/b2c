package co.yap.modules.dashboard.cards.addpaymentcard.interfaces

import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICards {

    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun loadJSONDummyList(): ArrayList<BenefitsModel>
    }

    interface View : IBase.View<ViewModel>
}