package co.yap.household.onboarding.onboardsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHOnBoardingSuccessVM @Inject constructor(override val state: HHOnBoardingSuccessState) :
    HiltBaseViewModel<IHHOnBoardingSuccess.State>(), IHHOnBoardingSuccess.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
