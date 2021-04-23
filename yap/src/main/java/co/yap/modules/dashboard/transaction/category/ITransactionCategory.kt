package co.yap.modules.dashboard.transaction.category

import android.app.Activity
import androidx.databinding.ObservableField
import co.yap.databinding.FragmentMerchantAnalyticsBinding
import co.yap.databinding.FragmentTransactionCategoryBinding
import co.yap.modules.dashboard.more.yapforyou.models.Achievement
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
        fun fetchTransactionCategories()
        var categoryAdapter : TransactionCategoryAdapter
        fun selectCategory(data : TapixCategory, position : Int)
        var tapixCategories: MutableList<TapixCategory>
        var selectedCategory: ObservableField<TapixCategory>
        var transactionId : ObservableField<String>
        var categoryName : ObservableField<String>
        fun updateCategory(context: Activity)
    }

    interface State : IBase.State
}