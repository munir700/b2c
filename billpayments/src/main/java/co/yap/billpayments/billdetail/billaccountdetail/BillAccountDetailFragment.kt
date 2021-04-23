package co.yap.billpayments.billdetail.billaccountdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.billdetail.base.BillDetailBaseFragment
import co.yap.billpayments.paybill.main.PayBillMainActivity
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.launchActivity

class BillAccountDetailFragment :
    BillDetailBaseFragment<IBillAccountDetail.ViewModel>(),
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
        viewModel.parentViewModel?.toolBarClickEvent?.observe(this, toolbarClickObserver)
    }

    private val onViewClickObserver = Observer<Int> {
        when (it) {
            R.id.btnPayNow -> {
                launchActivity<PayBillMainActivity> {
                    putExtra(ExtraKeys.BILL_MODEL.name, viewModel.parentViewModel?.selectedBill)
                }
            }
        }
    }

    private val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> navigate(
                destinationId = R.id.action_billAccountDetailFragment_to_editBillFragment
            )
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
