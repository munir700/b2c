package co.yap.sendMoney.fundtransfer.interfaces

import android.graphics.drawable.Drawable
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
        val updatedFee: MutableLiveData<String>
        var isAPIFailed: MutableLiveData<Boolean>
        var reasonPosition: Int
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        var receiverUUID: String
        var purposeOfPaymentList: MutableLiveData<ArrayList<PurposeOfPayment>>
        var feeType: String
        var feeTiers: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>
        fun handlePressOnView(id: Int)
        fun getTransferFees(productCode: String?)
        fun cashPayoutTransferRequest(beneficiaryId: Int?)
        fun getMoneyTransferLimits(productCode: String?)
        fun getPurposeOfPayment(productCode: String)
        fun getCountryLimit()
        fun getTransactionThresholds()
        fun proceedToTransferAmount()
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
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>>
        var produceCode: String?
    }
}