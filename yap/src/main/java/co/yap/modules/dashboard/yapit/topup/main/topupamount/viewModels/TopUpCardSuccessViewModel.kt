package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

import android.app.Application
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.ITopUpCardSuccess
import co.yap.modules.dashboard.yapit.topup.main.topupamount.states.TopUpCardSuccessState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class TopUpCardSuccessViewModel(application: Application) :
    BaseViewModel<ITopUpCardSuccess.State>(application), ITopUpCardSuccess.ViewModel {

    override val state: TopUpCardSuccessState = TopUpCardSuccessState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.buttonTitle="Go to dashboard"
    }


    override fun buttonClickEvent(id: Int) {
        clickEvent.postValue(id)
    }
}