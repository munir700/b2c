package co.yap.modules.dashboard.store.young.kyc

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungChildKycHomeVM @Inject constructor(override val state: IYoungChildKycHome.State) :
    DaggerBaseViewModel<IYoungChildKycHome.State>(),IYoungChildKycHome.ViewModel {
    override fun handleOnClick(id: Int) {

    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}