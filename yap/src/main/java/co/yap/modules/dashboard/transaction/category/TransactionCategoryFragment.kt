package co.yap.modules.dashboard.transaction.category

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTransactionCategoryBinding
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.getIcon
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionCategoryFragment : BaseBindingFragment<ITransactionCategory.ViewModel>(),
    ITransactionCategory.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_transaction_category

    override val viewModel: TransactionCategoryViewModel
        get() = ViewModelProviders.of(this).get(TransactionCategoryViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        setListener()
    }

    private fun initArguments() {
        arguments?.let { bundle ->
            val id = bundle.getString(Constants.TRANSACTION_ID)
            val name = bundle.getString(Constants.PRE_SELECTED_CATEGORY)
            viewModel.state.transactionId.set(id)
            viewModel.setPreSelectedCategory(name?:"General")
        }
    }

    private fun setListener() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.categoryAdapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                if (data is TapixCategory) viewModel.selectCategory(data, pos)
            }
        })
    }

    val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnConfirm -> {
                viewModel.updateCategory(requireActivity())
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                requireActivity().finish()
            }
        }
    }

    override fun getBinding() = (viewDataBinding as FragmentTransactionCategoryBinding)
}