package co.yap.modules.dashboard.transaction.detail

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.transaction.detail.ITransactionDetails
import co.yap.yapcore.BaseState

class TransactionDetailsState : BaseState(), ITransactionDetails.State {

    override var spentVisibility: ObservableField<Boolean> = ObservableField(false)

    @get:Bindable
    override var transactionNoteDate: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transactionNoteDate)
        }
    override var receiptTitle: ObservableField<String> = ObservableField("")
    override var txnNoteValue: ObservableField<String> = ObservableField()
    override var isTransferTxn: ObservableField<Boolean> = ObservableField(false)
    override var categoryTitle: ObservableField<String> = ObservableField("")
    override var categoryIcon: ObservableField<Int> = ObservableField()
    override var transactionTitle: ObservableField<String> = ObservableField()
    override var noteVisibility: ObservableBoolean = ObservableBoolean(false)
    override var receiptVisibility: ObservableBoolean = ObservableBoolean(false)
    override var exchangeRate: ObservableField<Double>? = ObservableField()
}