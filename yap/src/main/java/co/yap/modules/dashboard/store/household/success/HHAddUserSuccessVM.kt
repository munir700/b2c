package co.yap.modules.dashboard.store.household.success

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHAddUserSuccessVM @Inject constructor(override val state: IHHAddUserSuccess.State) :
    DaggerBaseViewModel<IHHAddUserSuccess.State>(), IHHAddUserSuccess.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.onBoardRequest?.value = it.getParcelable(HouseholdOnboardRequest::class.java.name)
        }
    }

}
