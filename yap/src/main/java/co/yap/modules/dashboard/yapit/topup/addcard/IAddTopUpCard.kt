package co.yap.modules.dashboard.yapit.topup.addcard

import co.yap.yapcore.IBase

interface IAddTopUpCard {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State {

    }
}