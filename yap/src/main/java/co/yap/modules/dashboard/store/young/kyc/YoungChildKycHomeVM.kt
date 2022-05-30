package co.yap.modules.dashboard.store.young.kyc

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungChildKycHomeVM @Inject constructor(override val state: YoungChildKycHomeState) :
    HiltBaseViewModel<IYoungChildKycHome.State>(),IYoungChildKycHome.ViewModel {
    override fun handleOnClick(id: Int) {

    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}