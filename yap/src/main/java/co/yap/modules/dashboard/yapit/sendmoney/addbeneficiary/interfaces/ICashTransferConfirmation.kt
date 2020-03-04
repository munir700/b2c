package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentCashTransferConfirmationBinding
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICashTransferConfirmation {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentCashTransferConfirmationBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var beneficiary: Beneficiary?
        var transactionThreshold: MutableLiveData<TransactionThresholdModel>
        var reasonCode: String
        var reason: String
        var transferNote: String
        fun handlePressOnView(id: Int)
        fun getTransactionThresholds()
        fun proceedToTransferAmount()
        fun getCutOffTimeConfiguration()
        fun uaeftsTransferRequest(beneficiaryId: String?)
    }

    interface State : IBase.State {
        var enteredAmount: ObservableField<String>
        var description: ObservableField<CharSequence>
        var cutOffTimeMsg: ObservableField<String>
        var productCode: ObservableField<String>
        var transferFee:ObservableField<String>
        var referenceNumber:ObservableField<String>
        var position:ObservableField<Int>
        var transferFeeDescription:ObservableField<CharSequence>
    }
}