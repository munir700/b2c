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
    override var categoryAdapter: TransactionCategoryAdapter =
        TransactionCategoryAdapter(mutableListOf())

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
        val list: MutableList<TapixCategory> = arrayListOf()
        list.add(TapixCategory(id = 0, categoryName = "Groceries", categoryIcon = ""))
        list.add(TapixCategory(id = 1, categoryName = "Transport", categoryIcon = "",isSelected =true))
        list.add(TapixCategory(id = 2, categoryName = "Eating out", categoryIcon = ""))
        list.add(TapixCategory(id = 3, categoryName = "Kids", categoryIcon = ""))
        list.add(TapixCategory(id = 4, categoryName = "Shopping", categoryIcon = ""))
        list.add(TapixCategory(id = 5, categoryName = "Personal", categoryIcon = ""))
        list.add(TapixCategory(id = 6, categoryName = "Entertainment", categoryIcon = ""))
        list.add(TapixCategory(id = 7, categoryName = "Services & utilities", categoryIcon = ""))
        list.add(TapixCategory(id = 8, categoryName = "Travel", categoryIcon = ""))
        list.add(TapixCategory(id = 9, categoryName = "Health", categoryIcon = ""))
        return list
    }

    override fun selectCategory(data: TapixCategory, position : Int) {
        categoryAdapter.getDataList().find {
            it.isSelected
        }.also {
            it?.isSelected = false
        }
        categoryAdapter.getDataList()[position].isSelected = true
        categoryAdapter.notifyDataSetChanged()    }
}
