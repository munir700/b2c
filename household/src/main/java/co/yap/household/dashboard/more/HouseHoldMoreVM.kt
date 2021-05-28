package co.yap.household.dashboard.more

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class HouseHoldMoreVM @Inject constructor(override val state: IHouseHoldMore.State) :
    BaseRecyclerAdapterVM<MoreOption, IHouseHoldMore.State>(), IHouseHoldMore.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {

    }
}