package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class EasyBankTransferWelcomeState : BaseState(),
    IEasyBankTransferWelcome.State {
    override var welcomeText: ObservableField<CharSequence> = ObservableField()
}