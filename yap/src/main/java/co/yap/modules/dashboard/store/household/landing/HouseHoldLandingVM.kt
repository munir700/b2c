package co.yap.modules.dashboard.store.household.landing

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HouseHoldLandingVM @Inject constructor(override val state: IHouseHoldLanding.State) :
    DaggerBaseViewModel<IHouseHoldLanding.State>(), IHouseHoldLanding.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override val clickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}