package co.yap.modules.dashboard.store.household.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldSuccess {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnShare(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var dummyStrings: Array<String>


    }
}