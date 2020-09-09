package co.yap.modules.dashboard.store.yapstore

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class YapStoreVM @Inject constructor(override var state: IYapStore.State
) : BaseRecyclerAdapterVM<Store , IYapStore.State>(), IYapStore.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override val clickEvent=SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}