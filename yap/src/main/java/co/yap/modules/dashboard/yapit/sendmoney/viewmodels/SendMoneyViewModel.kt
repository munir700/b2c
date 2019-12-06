package co.yap.modules.dashboard.yapit.sendmoney.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.ISendMoney
import co.yap.modules.dashboard.yapit.sendmoney.states.SendMoneyState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class SendMoneyViewModel(application: Application) :
    BaseViewModel<ISendMoney.State>(application),
    ISendMoney.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: SendMoneyState = SendMoneyState()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true

    }

}