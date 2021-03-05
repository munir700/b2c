package co.yap.modules.kyc.cardontheway

import co.yap.yapcore.IBase

interface ICardOnTheWay {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}