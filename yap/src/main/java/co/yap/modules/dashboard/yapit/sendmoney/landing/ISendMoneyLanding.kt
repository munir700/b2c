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
        val SEND_MONEY_TO_YAP_CONTACTS get() = 0
        val SEND_MONEY_TO_LOCALE_BANK get() = 1
        val SEND_MONEY_TO_INTERNATIONAL get() = 2
        val SEND_MONEY_TO_HOME_COUNTRY get() = 3
        val SEND_MONEY_QR_CODE get() = 4
        fun getBinding(): FragmentSendMoneyLandingBinding
        fun setObservers()
        fun removeObservers()
    }
}