package co.yap.modules.store.viewmodels

import android.app.Application
import co.yap.modules.store.interfaces.IYapStoreDetail
import co.yap.modules.store.states.YapStoreDetailState
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapStoreDetailViewModel(application: Application) :
    BaseViewModel<IYapStoreDetail.State>(application),
    IYapStoreDetail.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapStoreDetailState = YapStoreDetailState()
    override var yapStoreData: MutableList<Store> = mutableListOf(

    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}