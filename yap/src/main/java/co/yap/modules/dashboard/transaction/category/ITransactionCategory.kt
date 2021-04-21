package co.yap.modules.dashboard.transaction.category

import androidx.databinding.ObservableField
import co.yap.databinding.FragmentMerchantAnalyticsBinding
import co.yap.databinding.FragmentTransactionCategoryBinding
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionCategory {
    interface View : IBase.View<ViewModel>{
        fun getBinding(): FragmentTransactionCategoryBinding
    }
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun fetchTransactionCategories(): MutableList<TapixCategory>
        var categoryAdapter : TransactionCategoryAdapter
    }

    interface State : IBase.State
}