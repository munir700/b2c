package co.yap.modules.dashboard.home.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.yapcore.BaseState

class YapHomeState : BaseState(), IYapHome.State{

    @get:Bindable
    override var availableBalance: String = "0.00"
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalance)
        }
}