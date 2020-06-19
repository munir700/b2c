package co.yap.household.dashboard.more

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HouseHoldMoreVM @Inject constructor(override val state: IHouseHoldMore.State) :
    BaseRecyclerAdapterVM<MoreOption, IHouseHoldMore.State>(), IHouseHoldMore.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}