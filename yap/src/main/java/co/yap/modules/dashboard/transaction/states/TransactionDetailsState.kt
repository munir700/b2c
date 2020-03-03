package co.yap.modules.dashboard.transaction.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.yapcore.BaseState

class TransactionDetailsState : BaseState(), ITransactionDetails.State {

    @get:Bindable
    override var toolBarTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarTitle)
        }
    override var spentVisibility: ObservableField<Boolean> = ObservableField(false)
        set(value) {
            field = ObservableField(value)
        }

    override var txnNoteValue: ObservableField<String> = ObservableField()
    override var isYtoYTransfer: ObservableField<Boolean> = ObservableField(false)
    override var categoryTitle: ObservableField<String> = ObservableField("")
    override var categoryIcon: ObservableField<Int> = ObservableField()
}