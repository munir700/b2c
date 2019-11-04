package co.yap.modules.yapit.sendmoney.viewmodels

import android.app.Application
import co.yap.modules.yapit.sendmoney.interfaces.ISendMoney
import co.yap.modules.yapit.sendmoney.states.SendMoneyState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class SendMoneyViewModel(application: Application) :
    BaseViewModel<ISendMoney.State>(application),
    ISendMoney.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: SendMoneyState =
        SendMoneyState()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnAddButton(id: Int) {

    }
}