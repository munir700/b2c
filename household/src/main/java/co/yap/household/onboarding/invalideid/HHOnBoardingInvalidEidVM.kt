package co.yap.household.onboarding.invalideid

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingInvalidEidVM @Inject constructor(override val state: IHHOnBoardingInvalidEid.State) :
    DaggerBaseViewModel<IHHOnBoardingInvalidEid.State>(), IHHOnBoardingInvalidEid.ViewModel {
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
