package co.yap.modules.dashboard.addionalinfo.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.yapcore.BaseState

class AdditionalInfoState : BaseState(), IAdditionalInfo.State {
    override val title: ObservableField<String> = ObservableField("")
    override val subTitle: ObservableField<String> = ObservableField("")
    override val steps: ObservableField<Int> = ObservableField(2)
}