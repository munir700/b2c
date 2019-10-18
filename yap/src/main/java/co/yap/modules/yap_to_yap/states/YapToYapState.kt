package co.yap.modules.yap_to_yap.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.yap_to_yap.interfaces.IYapToYap
import co.yap.yapcore.BaseState

class YapToYapState: BaseState(),IYapToYap.State{

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