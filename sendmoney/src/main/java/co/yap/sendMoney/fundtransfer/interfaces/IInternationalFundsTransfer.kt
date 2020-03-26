package co.yap.sendMoney.fundtransfer.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalFundsTransfer {

    interface State : IBase.State {
        var transactionNote:ObservableField<String>
        var sourceCurrency:ObservableField<String>
        var destinationCurrency:ObservableField<String>
        var transferFeeSpannable: CharSequence?
        var etInputAmount: String?
        var etOutputAmount: String?
        var fromFxRate: String?
        var toFxRate: String?
        var valid: Boolean
        var maxLimit: Double?
        var minLimit: Double?
        var totalTransferAmount: ObservableField<Double>
        var errorDescription: String
        var availableBalanceString: CharSequence?
        fun clearError()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var isAPIFailed: MutableLiveData<Boolean>
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>>
        var reasonPosition: Int
        var transactionFeeResponse: MutableLiveData<RemittanceFeeResponse.RemittanceFee>
        var fxRateResponse: MutableLiveData<FxRateResponse.Data>
        var feeTiers: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>
        fun handlePressOnButton(id: Int)
        fun getTransactionFeeInternational(productCode: String?)
        fun getReasonList(productCode: String?)
        fun getTransactionInternationalfxList(productCode: String?)
        fun getMoneyTransferLimits(productCode: String?)
        fun getCountryLimits()
        fun getTransactionThresholds()

    }

    interface View : IBase.View<ViewModel>
}
