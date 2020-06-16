package co.yap.modules.dashboard.store.household.contact

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHAddUserContactVM @Inject constructor(override val state: IHHAddUserContact.State) :
    DaggerBaseViewModel<IHHAddUserContact.State>() {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}
