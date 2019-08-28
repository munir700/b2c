package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapStore
import co.yap.modules.dashboard.states.YapStoreState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapStoreViewModel(application: Application) : BaseViewModel<IYapStore.State>(application),
    IYapStore.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapStoreState = YapStoreState()

}