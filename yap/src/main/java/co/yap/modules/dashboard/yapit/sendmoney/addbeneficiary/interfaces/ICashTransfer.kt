package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICashTransfer {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>>
        var receiverUUID: String
        fun getTransactionFeeForCashPayout(productCode: String?)
        fun cashPayoutTransferRequest(beneficiaryId: String?)
        fun domesticTransferRequest(beneficiaryId: String?)

    }

    interface State : IBase.State {
        var amountBackground: Drawable?
        var feeAmountSpannableString: SpannableStringBuilder?
        var feeAmountString: String
        var amount: String
        var valid: Boolean
        var minLimit: Double
        var position: Int
        var availableBalance: String?
        var errorDescription: String
        var currencyType: String
        var maxLimit: Double
        var availableBalanceText: String
        var availableBalanceGuide: String
        var fullName: String
        var noteValue: String
        var imageUrl: String
        var feeStringVisibility: Boolean

        var transferFee: String
        var transferFeeSpannable: SpannableStringBuilder?
        var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>


        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>>
    }
}