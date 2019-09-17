package co.yap.modules.dashboard.cards.status.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapCardStatus {

    interface State : IBase.State {
        var valid: Boolean
        var ordered: Int
        var building: Int
        var shipping: Int
        var totalProgress: Int
        var buildingProgress: Int
        var shippingProgress: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel>
}