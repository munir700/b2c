package co.yap.modules.dashboard.yapit.y2y.transfer.interfaces

import android.graphics.drawable.Drawable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IY2YFundsTransfer {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun getBinding(): ViewDataBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        val transactionThreshold: MutableLiveData<TransactionThresholdModel>
        var receiverUUID: String
        val transferFundSuccess:MutableLiveData<Boolean>
        var enteredAmount:MutableLiveData<String>
        fun handlePressOnView(id: Int)
        fun getTransactionFee()
        fun getTransactionThresholds()
        fun proceedToTransferAmount()
        fun getTransactionLimits()

    }

    interface State : IBase.State {
        var amountBackground: Drawable?
        var valid: Boolean
        var minLimit: Double
        var availableBalance: String?
        var errorDescription: String
        var currencyType: String
        var maxLimit: Double
        var availableBalanceText: String
        var availableBalanceGuide: String
        var fullName: String
        var noteValue: String
        var imageUrl: String
        var transferFee: CharSequence?
    }
}