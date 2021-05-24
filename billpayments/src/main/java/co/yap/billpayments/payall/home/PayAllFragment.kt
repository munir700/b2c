package co.yap.billpayments.payall.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPayAllBinding
import co.yap.billpayments.payall.base.PayAllBaseFragment
import co.yap.yapcore.enums.BillPaymentStatus
import co.yap.yapcore.helpers.OverlapDecoration
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class PayAllFragment : PayAllBaseFragment<IPayAll.ViewModel>(),
    IPayAll.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_pay_all
    private val payAllViewModel: PayAllViewModel by viewModels()
    override val viewModel: PayAllViewModel
        get() = payAllViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().rvLogos.addItemDecoration(OverlapDecoration(-35))
    }

    override fun setObservers() {
        viewModel.parentViewModel?.allBills?.observe(this, Observer {
            getViewBinding().etAmount.setText(viewModel.parentViewModel?.allBills?.value?.sumByDouble { it.totalAmountDue?.toDouble() as Double }
                .toString().toFormattedCurrency(withComma = true))
            viewModel.populateData()
        })
        viewModel.clickEvent.observe(this, clickListener)
    }

    private val clickListener = Observer<Int> { it ->
        when (it) {
            R.id.btnPayAll -> {
                viewModel.payAllBills {
                    if (viewModel.parentViewModel?.paidBills?.count {
                                it.PaymentStatus.equals(
                                        BillPaymentStatus.DECLINED.title
                                )
                            } == 1) {
                        navigate(R.id.action_payAllFragment_to_singleDeclineFragment)
                    } else {
                        navigate(R.id.action_payAllFragment_to_payAllSuccessFragment)
                    }
                }
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getViewBinding(): FragmentPayAllBinding {
        return viewDataBinding as FragmentPayAllBinding
    }
}
