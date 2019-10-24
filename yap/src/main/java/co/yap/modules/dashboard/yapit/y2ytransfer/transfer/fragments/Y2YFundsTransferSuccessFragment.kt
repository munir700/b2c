package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.y2ytransfer.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YFundsTransferSuccess
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels.Y2YFundsTransferSuccessViewModel

class Y2YFundsTransferSuccessFragment : Y2YBaseFragment<IY2YFundsTransferSuccess.ViewModel>(),
    IY2YFundsTransferSuccess.View {
    val args: Y2YFundsTransferSuccessFragmentArgs by navArgs()
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer_success

    override val viewModel: IY2YFundsTransferSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(Y2YFundsTransferSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            activity?.finish()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.title = args.title
        viewModel.state.transferredAmount = args.currencyType + " " + args.amount

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }
}