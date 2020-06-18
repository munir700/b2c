package co.yap.modules.dashboard.store.household.landing

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldLanding {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }
    interface View : IBase.View<ViewModel>
}
