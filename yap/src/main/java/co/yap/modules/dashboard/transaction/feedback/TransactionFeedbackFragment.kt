package co.yap.modules.dashboard.transaction.feedback

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment

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

    }

    val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnDone -> {
                requireActivity().finish()
            }
        }
    }

    override fun removeObserver() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onToolBarClick(id: Int) {
        when(id){
            R.id.ivLeftIcon ->{
                requireActivity().finish()
            }

        }
    }
    override fun onDestroyView() {
        removeObserver()
        super.onDestroyView()
    }

}