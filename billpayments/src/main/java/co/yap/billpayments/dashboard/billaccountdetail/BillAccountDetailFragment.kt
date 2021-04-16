package co.yap.billpayments.dashboard.billaccountdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class BillAccountDetailFragment : PayBillBaseFragment<IBillAccountDetail.ViewModel>(),
    IBillAccountDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_bill_account_detail
    override val viewModel: BillAccountDetailViewModel
        get() = ViewModelProviders.of(this).get(BillAccountDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onViewClickObserver)
        viewModel.parentViewModel?.onToolbarClickEvent?.observe(this, toolbarClickObserver)
    }

    val onViewClickObserver = Observer<Int> {
        when (it) {
            R.id.btnPayNow -> {
                showToast("Pay Now ")
            }
        }
    }

    private val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> showToast("Edit Fragment")
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.parentViewModel?.toolBarClickEvent?.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
