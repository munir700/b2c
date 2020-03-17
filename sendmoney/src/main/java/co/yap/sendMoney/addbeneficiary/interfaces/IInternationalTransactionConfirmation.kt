package co.yap.sendmoney.addbeneficiary.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.sendmoney.addbeneficiary.fragments.InternationalTransactionConfirmationFragmentArgs
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalTransactionConfirmation {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun setData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val CREATE_OTP_SUCCESS_EVENT: Int
            get() = 1000
        val transactionThreshold: MutableLiveData<TransactionThresholdModel>
        val clickEvent: SingleClickEvent
        var otpAction: String?
        var beneficiary:Beneficiary?
        fun handlePressOnButtonClick(id: Int)
        fun rmtTransferRequest(beneficiaryId: String?)
        fun swiftTransferRequest(beneficiaryId: String?)
        fun createOtp()
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