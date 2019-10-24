package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.y2ytransfer.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YFundsTransferSuccess
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels.Y2YFundsTransferSuccessViewModel

class Y2YFundsTransferSuccessFragment : Y2YBaseFragment<IY2YFundsTransferSuccess.ViewModel>(),
    IY2YFundsTransferSuccess.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer_success

    override val viewModel: IY2YFundsTransferSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(Y2YFundsTransferSuccessViewModel::class.java)
}