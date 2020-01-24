package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments.InternationalTransactionConfirmationFragmentArgs
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

        fun handlePressOnButtonClick(id: Int)
        val clickEvent: SingleClickEvent
        fun rmtTransferRequest(beneficiaryId: String?)
        fun swiftTransferRequest(beneficiaryId: String?)
        fun createOtp()
        var otpAction: String?
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
        var args: InternationalTransactionConfirmationFragmentArgs?
    }
}