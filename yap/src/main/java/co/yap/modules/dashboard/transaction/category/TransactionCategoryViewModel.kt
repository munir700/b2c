package co.yap.modules.dashboard.transaction.category

import android.app.Application
import co.yap.modules.dashboard.transaction.receipt.add.IAddTransactionReceipt
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TransactionCategoryViewModel(application: Application) :
    BaseViewModel<ITransactionCategory.State>(application),ITransactionCategory.ViewModel {
    override val state: ITransactionCategory.State = TransactionCategoryState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}