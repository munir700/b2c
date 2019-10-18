package co.yap.modules.yapit.y2ytransfer.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.yapit.y2ytransfer.interfaces.IY2Y
import co.yap.yapcore.BaseState

class Y2YState : BaseState(), IY2Y.State {

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