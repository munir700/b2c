package co.yap.modules.dashboard.store.household.success

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHAddUserSuccess {
    interface State : IBase.State {
        var onBoardRequest: MutableLiveData<HouseholdOnboardRequest>?
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel>
}
