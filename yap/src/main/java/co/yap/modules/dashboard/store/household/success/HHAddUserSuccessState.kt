package co.yap.modules.dashboard.store.household.success

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.yapcore.BaseState

class HHAddUserSuccessState : BaseState(), IHHAddUserSuccess.State {
    override var onBoardRequest: MutableLiveData<HouseholdOnboardRequest>? = MutableLiveData()
}
