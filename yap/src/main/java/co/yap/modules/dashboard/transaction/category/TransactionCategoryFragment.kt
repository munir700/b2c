package co.yap.modules.dashboard.transaction.category

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentAddTransactionReceiptBinding
import co.yap.databinding.FragmentTransactionCategoryBinding
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionCategoryFragment : BaseBindingFragment<ITransactionCategory.ViewModel>(),
    ITransactionCategory.View {
    override fun getBindingVariable(): Int = -1

    override fun getLayoutId(): Int = R.layout.fragment_transaction_category

    override val viewModel: TransactionCategoryViewModel
        get() = ViewModelProviders.of(this).get(TransactionCategoryViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().rvCategories.adapter = viewModel.categoryAdapter
        setListner()
    }

    private fun setListner() {
        viewModel.categoryAdapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                if (data is TapixCategory)
                {
                val pre_selected = viewModel.fetchTransactionCategories().filter {
                     it.isSelected
                 }.also {
                     it?.isSelected = false
                }

                    viewModel.categoryAdapter.notifyDataSetChanged()
                }
                viewModel.clickEvent.setValue(view.id)
            }
        })
    }

    override fun onToolBarClick(id: Int) {
        when(id) {
         R.id.ivLeftIcon ->{
                activity?.finish()
            }
        }
    }
    override fun getBinding() = (viewDataBinding as FragmentTransactionCategoryBinding)

}