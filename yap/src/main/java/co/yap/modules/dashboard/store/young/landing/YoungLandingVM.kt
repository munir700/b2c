package co.yap.modules.dashboard.store.young.landing

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.store.household.landing.IHouseHoldLanding
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungLandingVM @Inject constructor(override val state: IYoungLanding.State) :
    DaggerBaseViewModel<IYoungLanding.State>(), IYoungLanding.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override val clickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}
