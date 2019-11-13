package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

import android.app.Application
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.IVerifyCardCvv
import co.yap.modules.dashboard.yapit.topup.main.topupamount.states.VerifyCardCvvState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class VerifyCardCvvViewModel(application: Application) :
    BaseViewModel<IVerifyCardCvv.State>(application), IVerifyCardCvv.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: VerifyCardCvvState = VerifyCardCvvState()

    override fun buttonClickEvent(id: Int) {
        clickEvent.postValue(id)
    }
}