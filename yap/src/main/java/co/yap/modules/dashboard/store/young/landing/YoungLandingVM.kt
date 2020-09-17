package co.yap.modules.dashboard.store.young.landing

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungLandingVM @Inject constructor(override val state: IYoungLanding.State) :
    DaggerBaseViewModel<IYoungLanding.State>(), IYoungLanding.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun handleOnClick(id: Int) {
    }
}
