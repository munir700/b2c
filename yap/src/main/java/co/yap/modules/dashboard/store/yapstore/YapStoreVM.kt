package co.yap.modules.dashboard.store.yapstore

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.hilt.base.viewmodel.BaseRecyclerAdapterVMV2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YapStoreVM @Inject constructor(override var state: YapStoreState
) : BaseRecyclerAdapterVMV2<Store , IYapStore.State>(), IYapStore.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}