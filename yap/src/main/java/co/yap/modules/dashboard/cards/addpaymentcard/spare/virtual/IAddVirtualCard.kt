package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import co.yap.yapcore.IBase

interface IAddVirtualCard {
    interface State : IBase.State{
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}