package co.yap.modules.dashboard.yapit.sendmoney.landing

import co.yap.databinding.FragmentSendMoneyLandingBinding
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLandingOptions
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISendMoneyLanding {
    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        var landingAdapter: SendMoneyLandingAdapter
        fun geSendMoneyOptions(): MutableList<SendMoneyLandingOptions>
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface View : IBase.View<ViewModel> {
        val sendMoneyToYAPContacts get() = 0
        val sendMoneyToLocalBank get() = 1
        val sendMoneyToInternational get() = 2
        val sendMoneyToHomeCountry get() = 3
        val sendMoneyQRCode get() = 4
        fun getBinding(): FragmentSendMoneyLandingBinding
        fun setObservers()
        fun removeObservers()
    }
}