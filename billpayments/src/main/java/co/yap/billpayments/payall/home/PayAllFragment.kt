package co.yap.billpayments.payall.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPayAllBinding
import co.yap.billpayments.payall.base.PayAllBaseFragment
import co.yap.billpayments.payall.payallsuccess.bottomsheetloder.PayBillLoderBottomSheet
import co.yap.yapcore.enums.BillPaymentStatus
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import com.google.android.material.snackbar.Snackbar

class PayAllFragment : PayAllBaseFragment<IPayAll.ViewModel>(),
    IPayAll.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_all
    override val viewModel: PayAllViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.parentViewModel?.allBills?.observe(this, Observer {
            getViewBinding().etAmount.setText(viewModel.parentViewModel?.allBills?.value?.sumByDouble { it.totalAmountDue?.toDouble() as Double }
                .toString().toFormattedCurrency(withComma = true, showCurrency = false))
            viewModel.populateData()
        })
        viewModel.clickEvent.observe(this, clickListener)
        viewModel.errorEvent.observe(this, errorEvent)
        if (!viewModel.isBalanceAvailable(viewModel.parentViewModel?.allBills?.value?.sumByDouble { it.totalAmountDue?.toDouble() as Double }!!)) {
            viewModel.state.valid.set(false)
            viewModel.showBalanceNotAvailableError(
                viewModel.parentViewModel?.allBills?.value?.sumByDouble { it.totalAmountDue?.toDouble() as Double }!!
                    .toString()
            )
        } else viewModel.state.valid.set(true)

    }

    val errorEvent = Observer<String> {
        if (!it.isNullOrEmpty())
            showErrorSnackBar(it)
        else
            hideErrorSnackBar()
    }

    private val clickListener = Observer<Int> { it ->
        when (it) {
            R.id.btnPayAll -> {
                viewModel.payAllBills(viewModel.getPayAllBillsRequest()) {

                    if (viewModel.parentViewModel?.paidBills?.size == 1 && viewModel.parentViewModel?.paidBills?.count {
                            it.paymentStatus.equals(
                                BillPaymentStatus.FAILEDTITLE.title
                            )
                        } == 1) {
                        navigate(R.id.action_payAllFragment_to_singleDeclineFragment)
                    } else {
                        navigate(R.id.action_payAllFragment_to_payAllStatusFragment)
                    }
                }
                //openBottomSheet()
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
        cancelAllSnackBar()
    }

    override fun getViewBinding(): FragmentPayAllBinding {
        return viewDataBinding as FragmentPayAllBinding
    }

    private fun showErrorSnackBar(errorMessage: String) {
        showTextUpdatedAbleSnackBar(
            errorMessage,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    private fun hideErrorSnackBar() {
        cancelAllSnackBar()
    }

    private fun openBottomSheet() {
        fragmentManager.let {
            val payBillLoderBottomSheet =
                PayBillLoderBottomSheet()
            it?.let { it1 -> payBillLoderBottomSheet.show(it1, "") }
        }
    }
}
