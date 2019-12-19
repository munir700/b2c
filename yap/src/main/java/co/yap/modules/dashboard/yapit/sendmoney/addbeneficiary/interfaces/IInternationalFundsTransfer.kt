package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.text.SpannableStringBuilder
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalFundsTransfer {

    interface State : IBase.State {
        var beneficiaryName: String
        var beneficiaryPicture: String
        var nameInitialsVisibility: Int
        var senderCurrency: String?
        var beneficiaryCurrency: String
        var beneficiaryCountry: String?
        var senderAmount: String
        var beneficiaryAmount: String
        var transferFee: String
        var transferFeeSpannable: SpannableStringBuilder?
        var fxRateAmount: String?
        var receiverCurrency: String?
        var receiverCurrencyAmount: String?
        var receiverCurrencyAmountFxRate: String?

        var fromFxRate: String?
        var fromFxRateCurrency: String?
        var toFxRate: String?
        var rate: String?
        var toFxRateCurrency: String?
        var valid: Boolean
        var beneficiaryId: String?
        var listItemSelectedCart: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>
        var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>

    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList>
        val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>>
        fun handlePressOnButton(id:Int)
        fun getTransactionFeeInternational(productCode:String?)
        fun rmtTransferRequest(beneficiaryId: String?)
        fun swiftTransferRequest(beneficiaryId: String?)
        var otpAction: String?
    }

    interface View : IBase.View<ViewModel>
}