package co.yap.modules.dashboard.home.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.yapcore.BaseState

class YapHomeState : BaseState(), IYapHome.State {

    @get:Bindable
    override var availableBalance: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalance)
        }
    override var filterCount: ObservableField<Int> = ObservableField()
    override var isTransEmpty: ObservableField<Boolean> = ObservableField(true)
    override var isUserAccountActivated: ObservableField<Boolean> = ObservableField(false)
}