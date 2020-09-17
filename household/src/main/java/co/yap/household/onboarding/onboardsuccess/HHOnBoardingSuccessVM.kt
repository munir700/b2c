package co.yap.household.onboarding.onboardsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingSuccessVM @Inject constructor(override val state: IHHOnBoardingSuccess.State) :
    DaggerBaseViewModel<IHHOnBoardingSuccess.State>(), IHHOnBoardingSuccess.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
