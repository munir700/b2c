package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBankList {
    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentEasyBankTransferBankListBinding
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val bankList: MutableLiveData<MutableList<BankListMainModel>>
        val bankListAdapter: BankListAdapter
        var leanOnBoardModel: LeanOnBoardModel
        fun handlePressOnView(id: Int)
        fun getBankList()
    }

    interface State : IBase.State {}
}