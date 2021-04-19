package co.yap.modules.dashboard.transaction.category

import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.yapcore.BaseBindingFragment

class TransactionCategoryFragment : BaseBindingFragment<ITransactionCategory.ViewModel>(),
    ITransactionCategory.View {
    override fun getBindingVariable(): Int = -1

    override fun getLayoutId(): Int = R.layout.fragment_transaction_category

    override val viewModel: ITransactionCategory.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionCategoryViewModel::class.java)
}