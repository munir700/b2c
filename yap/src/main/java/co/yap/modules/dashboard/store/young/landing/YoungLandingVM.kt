package co.yap.modules.dashboard.store.young.landing

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungLandingVM @Inject constructor(override val state: YoungLandingState) :
    HiltBaseViewModel<IYoungLanding.State>(), IYoungLanding.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun handleOnClick(id: Int) {
    }
}
