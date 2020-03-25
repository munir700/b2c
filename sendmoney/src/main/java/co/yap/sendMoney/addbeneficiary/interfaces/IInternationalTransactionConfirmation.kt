package co.yap.sendMoney.addbeneficiary.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.sendMoney.addbeneficiary.fragments.InternationalTransactionConfirmationFragmentArgs
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalTransactionConfirmation {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun setData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val transactionThreshold: MutableLiveData<TransactionThresholdModel>
        val clickEvent: SingleClickEvent
        var beneficiary:Beneficiary?
        val isOtpRequired: MutableLiveData<Boolean>
        fun handlePressOnButtonClick(id: Int)
        fun rmtTransferRequest(beneficiaryId: String?)
        fun swiftTransferRequest(beneficiaryId: String?)
        fun requestForTransfer()
        fun getTransactionThresholds()
        fun proceedToTransferAmount()
        fun getCutOffTimeConfiguration()
    }

    interface State : IBase.State {
        var name: String?
        var picture: String?
        var position: Int?
        var flagLayoutVisibility: Boolean?
        var transferDescription: CharSequence?
        var referenceNumber: String?
        var confirmHeading: String?
        var receivingAmountDescription: CharSequence?
        var transferFeeDescription: CharSequence?
        var beneficiaryCountry: String?
        var cutOffTimeMsg: String?
        var args: InternationalTransactionConfirmationFragmentArgs?

    }
}