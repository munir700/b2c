package co.yap.modules.transaction_filters.viewmodels

import android.app.Application
import co.yap.modules.transaction_filters.interfaces.ITransactionFilters
import co.yap.modules.transaction_filters.states.TransactionFiltersState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TransactionFiltersViewModel(application: Application) :
    BaseViewModel<ITransactionFilters.State>(application),
    ITransactionFilters.ViewModel {

    override val state: ITransactionFilters.State = TransactionFiltersState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}