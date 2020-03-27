package co.yap.sendMoney.fundtransfer.interfaces

import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICashTransfer {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var isAPIFailed:MutableLiveData<Boolean>
        var reasonPosition: Int
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>>
        var receiverUUID: String
        var transactionThreshold: MutableLiveData<TransactionThresholdModel>
        var purposeOfPaymentList: MutableLiveData<ArrayList<PurposeOfPayment>>
        var transactionFeeResponse: MutableLiveData<RemittanceFeeResponse.RemittanceFee>
        var feeTiers: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>
        fun handlePressOnView(id: Int)
        fun getTransferFees(productCode: String?)
        fun cashPayoutTransferRequest(beneficiaryId: Int?)
        fun getMoneyTransferLimits(productCode: String?)
        fun getCountryLimit()
        fun getTransactionThresholds()
        fun proceedToTransferAmount()
        fun getCashTransferReasonList()
        fun getPurposeOfPayment()
        fun processPurposeList(list: ArrayList<PurposeOfPayment>)

    }

    interface State : IBase.State {
        var amountBackground: Drawable?
        var feeAmountSpannableString: CharSequence?
        var availableBalanceString: CharSequence?
        var amount: String
        var valid: Boolean
        var minLimit: Double
        var errorDescription: String
        var maxLimit: Double
        var noteValue: String?
        fun clearError()
        var totalAmountWithFee: ObservableField<Double>
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>>
        var produceCode: String?
        var isDefaultFeeApplicable: ObservableField<Boolean>
    }
}