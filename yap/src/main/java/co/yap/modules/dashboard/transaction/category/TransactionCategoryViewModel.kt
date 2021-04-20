package co.yap.modules.dashboard.transaction.category

import android.app.Application
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TransactionCategoryViewModel(application: Application) :
    BaseViewModel<ITransactionCategory.State>(application), ITransactionCategory.ViewModel {
    override val state: TransactionCategoryState = TransactionCategoryState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    val repository: TransactionsRepository = TransactionsRepository
    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = "Select Category"
        categoryAdapter.setList(fetchTransactionCategories())
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchTransactionCategories(): MutableList<TapixCategory> {
        return arrayListOf(
            TapixCategory(id = 0,name = "Groceries",icon = ""),
            TapixCategory(id = 1,name = "Transport",icon = ""),
            TapixCategory(id = 2,name = "Eating out",icon = ""),
            TapixCategory(id = 3,name = "Kids",icon = ""),
            TapixCategory(id = 4,name = "Shopping",icon = ""),
            TapixCategory(id = 5,name = "Personal",icon = ""),
            TapixCategory(id = 6,name = "Entertainment",icon = ""),
            TapixCategory(id = 7,name = "Services & utilities",icon = ""),
            TapixCategory(id = 8,name = "Travel",icon = ""),
            TapixCategory(id = 9,name = "Health",icon = "")
        )
    }

    override val categoryAdapter: TransactionCategoryAdapter = TransactionCategoryAdapter(
        mutableListOf())
}