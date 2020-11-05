package co.yap.modules.dashboard.yapit.sendmoney.landing

import co.yap.yapcore.IBase

interface ISendMoneyLanding {
    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State>{
        val landingAdapter: SendMoneyLandingAdapter
    }

    interface View : IBase.View<ViewModel>
}