package co.yap.modules.dashboard.more.yapforyou.states

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.yapcore.BaseState

class YapForYouMainState : BaseState(), IYapForYouMain.State {
    override var rightIcon: ObservableField<Int> = ObservableField(-1)
    override var leftIcon: ObservableField<Int> = ObservableField(-1)
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(false)
}