package co.yap.app.modules.login.states

import androidx.databinding.ObservableField
import co.yap.app.modules.login.interfaces.IOtpBlockedInfo
import co.yap.yapcore.BaseState

class OtpBlockedInfoState : BaseState(), IOtpBlockedInfo.State {
    override val userFirstName: ObservableField<String> = ObservableField()
    override val helpPhoneNo: ObservableField<String> = ObservableField()
    override var token: ObservableField<String> = ObservableField()

}