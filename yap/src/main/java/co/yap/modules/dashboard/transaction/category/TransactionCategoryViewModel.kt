package co.yap.modules.dashboard.transaction.category

import android.app.Application
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager

class TransactionCategoryViewModel(application: Application) :
    BaseViewModel<ITransactionCategory.State>(application), ITransactionCategory.ViewModel {
    override val state: TransactionCategoryState = TransactionCategoryState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var categoryAdapter: TransactionCategoryAdapter =
        TransactionCategoryAdapter(mutableListOf())

    val repository: TransactionsRepository = TransactionsRepository

    override var tapixCategories: MutableList<TapixCategory> = arrayListOf()

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = "Select Category"
        categoryAdapter.setList(fetchTransactionCategories())
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchTransactionCategories(): MutableList<TapixCategory> {
        /*launch {
            state.loading = true
            when (val response = repository.getAllTransactionCategories()) {
                is RetroApiResponse.Success -> {
                    response.data.txnCategories?.let { tapixCategories.addAll(it)
                    categoryAdapter.setList(tapixCategories)}
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
            }
        }*/
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
