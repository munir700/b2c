package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk.LeanSdkInitializer
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.IBase

interface IBankList {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val bankList: MutableLiveData<MutableList<BankListMainModel>>
        val bankListAdapter: BankListAdapter
        var leanOnBoardModel: LeanOnBoardModel
        var isPaymentJourneySet: MutableLiveData<Boolean>
        var leanSdkInitializer: LeanSdkInitializer
        fun getBankList()
        fun startPaymentSourceJourney(bankIdentifier: String, activity: Activity?)
    }

    interface State : IBase.State {
        var isSearchActive: MutableLiveData<Boolean>
    }
}