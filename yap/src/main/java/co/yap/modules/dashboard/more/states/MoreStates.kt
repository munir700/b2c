package co.yap.modules.dashboard.more.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.more.interfaces.IMore
import co.yap.yapcore.BaseState

class MoreStates : BaseState(), IMore.State {

    @get:Bindable
    override var tootlBarTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarTitle)

        }

    @get:Bindable
    override var tootlBarVisibility: Int = 0x00000000
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarVisibility)

        }
}