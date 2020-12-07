package co.yap.modules.dashboard.addreceipt

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment

class AddTransactionReceiptFragment : BaseBindingFragment<IAddTransactionReceipt.ViewModel>(),
    IAddTransactionReceipt.View {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_add_transaction_receipt
    override val viewModel: IAddTransactionReceipt.ViewModel
        get() = ViewModelProviders.of(this).get(AddTransactionReceiptViewModel::class.java)

}