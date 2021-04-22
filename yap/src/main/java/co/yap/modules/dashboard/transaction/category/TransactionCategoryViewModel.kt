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
    override var selectedCategory: TapixCategory? = null

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = "Select Category"
        fetchTransactionCategories()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchTransactionCategories() {
        launch {
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
        }
    }

    override fun selectCategory(data: TapixCategory, position : Int) {
        categoryAdapter.getDataList().find {
            it.isSelected
        }.also {
            it?.isSelected = false
        }
        selectedCategory = data
        categoryAdapter.getDataList()[position].isSelected = true
        categoryAdapter.notifyDataSetChanged()    }
}
