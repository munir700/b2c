package co.yap.household.setpin.setpinstart

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPinCardReview {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handleClick(id: Int)
        var clickEvent: SingleClickEvent
    }

    interface State : IBase.State
}