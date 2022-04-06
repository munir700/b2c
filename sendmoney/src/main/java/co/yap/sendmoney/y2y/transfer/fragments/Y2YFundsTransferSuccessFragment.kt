package co.yap.sendmoney.y2y.transfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentY2yFundsTransferSuccessBinding
import co.yap.sendmoney.y2y.main.fragments.Y2YBaseFragment
import co.yap.sendmoney.y2y.transfer.interfaces.IY2YFundsTransferSuccess
import co.yap.sendmoney.y2y.transfer.viewmodels.Y2YFundsTransferSuccessViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager

class Y2YFundsTransferSuccessFragment :
    Y2YBaseFragment<FragmentY2yFundsTransferSuccessBinding, IY2YFundsTransferSuccess.ViewModel>(),
    IY2YFundsTransferSuccess.View {
    private val args: Y2YFundsTransferSuccessFragmentArgs by navArgs()
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer_success

    override val viewModel: IY2YFundsTransferSuccess.ViewModel
        get() = ViewModelProvider(this).get(Y2YFundsTransferSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.updateCardBalance {}
        viewModel.clickEvent.observe(this, Observer {
            setResultAndFinish()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()

    }

    private fun setUpData() {
        viewModel.state.title = args.title
        viewModel.state.transferredAmount =
            args.amount.toFormattedCurrency(showCurrency = true, currency = args.currencyType)
        viewModel.state.imageUrl = args.imagePath

        viewDataBinding.lyUserImage.tvNameInitials.background = Utils.getContactBackground(
            viewDataBinding.lyUserImage.tvNameInitials.context,
            args.position
        )


        viewDataBinding.lyUserImage.tvNameInitials.setTextColor(
            Utils.getContactColors(
                viewDataBinding.lyUserImage.tvNameInitials.context, args.position
            )
        )
    }

    private fun setResultAndFinish() {
        val intent = Intent()
        intent.putExtra(Constants.MONEY_TRANSFERED, true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    override fun onBackPressed(): Boolean {
        return true
    }
}