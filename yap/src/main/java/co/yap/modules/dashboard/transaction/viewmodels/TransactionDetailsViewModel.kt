package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.yapcore.BaseViewModel


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {
    override val state: TransactionDetailsState = TransactionDetailsState()
}