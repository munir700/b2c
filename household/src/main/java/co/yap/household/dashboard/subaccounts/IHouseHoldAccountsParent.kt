package co.yap.household.dashboard.subaccounts

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldAccountsParent {
    interface View : IBase.View<ViewModel>{
        fun setObservers()
        fun removeObservers()
    }
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnButton(id: Int)
        var clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var toolBarTitle: ObservableField<String>
    }
}