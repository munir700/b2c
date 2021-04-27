package co.yap.modules.dashboard.transaction.feedback

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionFeedbackFragment : BaseBindingFragment<ITransactionFeedback.ViewModel>(),
    ITransactionFeedback.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_transaction_feedback
    override val viewModel: ITransactionFeedback.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionFeedbackViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }

    override fun setObserver() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.adapter.setItemListener(onClickItem)
    }


    val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnDone -> {
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            }
        }
    }
    val onClickItem = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.cbRequireTransaction -> {
                viewModel.selectFeedback(pos)
                }
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

    override fun removeObserver() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroyView() {
        removeObserver()
        super.onDestroyView()
    }

}