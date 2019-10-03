package co.yap.modules.dashboard.more.help.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.yapcore.BaseState

class HelpSupportState : BaseState(), IHelpSupport.State {

    override var title: ObservableField<String> = ObservableField()
}