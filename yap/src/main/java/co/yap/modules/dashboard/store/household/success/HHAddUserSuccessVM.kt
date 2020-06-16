package co.yap.modules.dashboard.store.household.success

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHAddUserSuccessVM @Inject constructor(override val state: IHHAddUserSuccess.State) :
    DaggerBaseViewModel<IHHAddUserSuccess.State>() {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}
