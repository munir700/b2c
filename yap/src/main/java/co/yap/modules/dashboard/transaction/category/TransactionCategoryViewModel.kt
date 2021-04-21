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
        list.add(TapixCategory(id = 0, name = "Groceries", icon = ""))
        list.add(TapixCategory(id = 1, name = "Transport", icon = "",isSelected =true))
        list.add(TapixCategory(id = 2, name = "Eating out", icon = ""))
        list.add(TapixCategory(id = 3, name = "Kids", icon = ""))
        list.add(TapixCategory(id = 4, name = "Shopping", icon = ""))
        list.add(TapixCategory(id = 5, name = "Personal", icon = ""))
        list.add(TapixCategory(id = 6, name = "Entertainment", icon = ""))
        list.add(TapixCategory(id = 7, name = "Services & utilities", icon = ""))
        list.add(TapixCategory(id = 8, name = "Travel", icon = ""))
        list.add(TapixCategory(id = 9, name = "Health", icon = ""))
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
