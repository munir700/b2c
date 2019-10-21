package co.yap.modules.transaction_filters.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.transaction_filters.interfaces.ITransactionFilters
import co.yap.modules.transaction_filters.viewmodels.TransactionFiltersViewModel
import co.yap.BR
import co.yap.yapcore.BaseBindingFragment

class TransactionFiltersFragment : BaseBindingFragment<ITransactionFilters.ViewModel>(),
    ITransactionFilters.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_transaction_filters

    override val viewModel: ITransactionFilters.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionFiltersViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}