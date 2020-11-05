package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.landing.ISendMoneyLanding
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyLandingAdapter
import co.yap.modules.dashboard.yapit.sendmoney.landing.states.SendMoneyLandingState
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyBaseViewMode

class SendMoneyLandingViewModel(application: Application) :
    SendMoneyBaseViewMode<ISendMoneyLanding.State>(application),
    ISendMoneyLanding.ViewModel {
    override val landingAdapter: SendMoneyLandingAdapter = SendMoneyLandingAdapter(
        context,
        mutableListOf()
    )
    override val state: ISendMoneyLanding.State = SendMoneyLandingState()
}