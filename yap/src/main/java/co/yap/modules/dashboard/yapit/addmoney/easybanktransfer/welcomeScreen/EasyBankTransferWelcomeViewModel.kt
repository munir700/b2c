package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import android.app.Application
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.yapcore.SingleClickEvent

class EasyBankTransferWelcomeViewModel(application: Application) :
    AddMoneyBaseViewModel<IEasyBankTransferWelcome.State>(application),
    IEasyBankTransferWelcome.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: IEasyBankTransferWelcome.State = EasyBankTransferWelcomeState()
}