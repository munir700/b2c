package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import androidx.databinding.ObservableField
import co.yap.databinding.FragmentEasyBankTransferWelcomeBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEasyBankTransferWelcome {
    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentEasyBankTransferWelcomeBinding
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun setDataFormat()
    }

    interface State : IBase.State {
        var welcomeText: ObservableField<CharSequence>
    }
}