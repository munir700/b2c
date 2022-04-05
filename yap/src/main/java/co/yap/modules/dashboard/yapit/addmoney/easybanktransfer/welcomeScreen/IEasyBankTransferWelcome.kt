package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentEasyBankTransferWelcomeBinding
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
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
        val leanOnBoardModel: MutableLiveData<LeanOnBoardModel>
        fun handlePressOnView(id: Int)
        fun setDataFormat()
        fun onboardUser()
    }

    interface State : IBase.State {
        var welcomeText: ObservableField<CharSequence>
    }
}