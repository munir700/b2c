package co.yap.household.onboard.onboarding.kycsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class KycSuccessVM @Inject constructor() :
    DaggerBaseViewModel<IKycSuccess.State>(), IKycSuccess.ViewModel {
    override val state = KycSuccessState()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}