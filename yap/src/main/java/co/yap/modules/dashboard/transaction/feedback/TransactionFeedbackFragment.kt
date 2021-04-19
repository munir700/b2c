package co.yap.modules.dashboard.transaction.feedback

import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.transaction.receipt.add.IAddTransactionReceipt
import co.yap.yapcore.BaseBindingFragment
import com.digitify.identityscanner.camera.CameraListener

class TransactionFeedbackFragment : BaseBindingFragment<ITransactionFeedback.ViewModel>(),
    ITransactionFeedback.View {
    override fun getBindingVariable(): Int = BR.viewModel


    override fun getLayoutId(): Int = R.layout.fragment_transaction_feedback

    override val viewModel: ITransactionFeedback.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionFeedbackViewModel::class.java)

}