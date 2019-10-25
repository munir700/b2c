package co.yap.modules.dashboard.transaction.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.yapcore.BaseState

class TransactionDetailsState : BaseState(), ITransactionDetails.State {
    @get:Bindable
    override var toolBarTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarTitle)
        }
}