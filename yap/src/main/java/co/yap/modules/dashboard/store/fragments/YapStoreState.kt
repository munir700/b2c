package co.yap.modules.dashboard.store.fragments

import androidx.databinding.ObservableBoolean
import co.yap.modules.dashboard.store.fragments.IYapStore
import co.yap.yapcore.BaseState

class YapStoreState : BaseState(), IYapStore.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean()
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean()
}
