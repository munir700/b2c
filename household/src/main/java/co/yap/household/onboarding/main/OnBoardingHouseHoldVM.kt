package co.yap.household.onboarding.main

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class OnBoardingHouseHoldVM @Inject constructor(override val state: OnBoardingHouseHoldState) :
    DaggerBaseViewModel<IOnBoardingHouseHold.State>(), IOnBoardingHouseHold.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}