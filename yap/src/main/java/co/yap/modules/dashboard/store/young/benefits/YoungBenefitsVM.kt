package co.yap.modules.dashboard.store.young.benefits

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.hilt.base.viewmodel.BaseRecyclerAdapterVMV2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungBenefitsVM @Inject constructor(override var state: YoungBenefitsState) :
    BaseRecyclerAdapterVMV2<YoungBenefitsModel, IYoungBenefits.State>(), IYoungBenefits.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {

    }
}
