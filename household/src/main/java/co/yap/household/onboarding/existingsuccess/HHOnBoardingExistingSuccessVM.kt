package co.yap.household.onboarding.existingsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingExistingSuccessVM @Inject constructor(override val state: IHHOnBoardingExistingSuccess.State) :
    DaggerBaseViewModel<IHHOnBoardingExistingSuccess.State>(),
    IHHOnBoardingExistingSuccess.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}