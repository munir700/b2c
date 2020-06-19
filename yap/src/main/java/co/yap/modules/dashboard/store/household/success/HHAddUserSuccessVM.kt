package co.yap.modules.dashboard.store.household.success

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHAddUserSuccessVM @Inject constructor(override val state: IHHAddUserSuccess.State) :
    DaggerBaseViewModel<IHHAddUserSuccess.State>(), IHHAddUserSuccess.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.onBoardRequest?.value = it.getParcelable(HouseholdOnboardRequest::class.java.name)
        }
    }
}
