package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamountscreen

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk.LeanSdkInitializer
import co.yap.networking.leanteach.requestdtos.GetPaymentIntentIdModel
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.TransferLimits
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITopupAmount {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var customerId: String?
        var paymentIntentId: MutableLiveData<String>
        var leanPaymentStatus: MutableLiveData<Boolean>
        var getPaymentIntentModel: GetPaymentIntentIdModel
        var bankListMainModel: BankListMainModel
        var leanCustomerAccounts: LeanCustomerAccounts?
        var leanSdkInitializer: LeanSdkInitializer
        fun denominationAmountValidator(amount: String)
        fun handleClickEvent(id: Int)
        fun setAvailableBalance(balance: String)
        fun getPaymentIntentId()
        fun getLimitOfAmount(): TransferLimits?
        fun isMaxMinLimitReached(): Boolean
        fun startTopUpJourney(id: String, activity: Activity)
    }

    interface State : IBase.State {
        val denominationChipList: MutableLiveData<List<String>>
        val enteredTopUpAmount: MutableLiveData<String>
        val availableBalance: MutableLiveData<CharSequence>
    }
}