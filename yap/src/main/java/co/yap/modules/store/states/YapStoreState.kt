package co.yap.modules.store.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.store.interfaces.IYapStore
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseState

class YapStoreState : BaseState(), IYapStore.State {

    @get:Bindable
    override var storesList: MutableList<Store> = mutableListOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.storesList)
        }

}
