package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import androidx.lifecycle.MutableLiveData
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.IBase

interface IBankList {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val bankList: MutableLiveData<MutableList<BankListMainModel>>
        val bankListAdapter: BankListAdapter
        var leanOnBoardModel: LeanOnBoardModel
        var isPaymentJourneySet: MutableLiveData<Boolean>
        fun getBankList()
        fun startPaymentSourceJourney(bankIdentifier: String)
    }

    interface State : IBase.State {}
}