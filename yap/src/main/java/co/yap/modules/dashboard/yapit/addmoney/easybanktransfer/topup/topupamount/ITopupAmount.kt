package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist.BankListAdapter
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITopupAmount {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        val denominationFirstAmount:String
        val denominationSecondAmount:String
        val denominationThirdAmount:String
        val valid:MutableLiveData<Boolean>
        val availableBalance: MutableLiveData<String>
    }
}