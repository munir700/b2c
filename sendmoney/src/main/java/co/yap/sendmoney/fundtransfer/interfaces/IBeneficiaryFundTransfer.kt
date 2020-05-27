package co.yap.sendmoney.fundtransfer.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.fundtransfer.models.TransferFundData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBeneficiaryFundTransfer {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        var errorEvent: MutableLiveData<String>
        var beneficiary: MutableLiveData<Beneficiary>
        var transferData: MutableLiveData<TransferFundData>
        var transactionThreshold: MutableLiveData<TransactionThresholdModel>
        var transactionWillHold: Boolean
        var selectedPop: PurposeOfPayment?
    }

    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIcon: ObservableBoolean
        var leftIcon: ObservableBoolean
        var rightButtonText: String?
        var toolBarTitle: String?
        var position: Int
    }
}