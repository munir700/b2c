package co.yap.modules.dashboard.yapit.y2y.home.states

import androidx.databinding.ObservableBoolean
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.yapcore.BaseState

class YapToYapState : BaseState(), IYapToYap.State {
    override var isRecentsVisible: ObservableBoolean = ObservableBoolean(false)
}