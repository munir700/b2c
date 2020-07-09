package co.yap.household.onboarding.onboardsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingSuccessVM @Inject constructor(override val state: IHHOnBoardingSuccess.State) :
    DaggerBaseViewModel<IHHOnBoardingSuccess.State>(), IHHOnBoardingSuccess.ViewModel {
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
    }
    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }
}
