package co.yap.billpayments.billdetail.billaccountdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.billdetail.base.BillDetailBaseFragment
import co.yap.billpayments.paybill.main.PayBillMainActivity
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.launchActivity

class BillAccountDetailFragment :
    BillDetailBaseFragment<IBillAccountDetail.ViewModel>(),
    IBillAccountDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_bill_account_detail
    override val viewModel: BillAccountDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.getBillAccountHistory(viewModel.parentViewModel?.selectedBill?.uuid.toString())
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onViewClickObserver)
        viewModel.parentViewModel?.toolBarClickEvent?.observe(this, toolbarClickObserver)
    }

    private val onViewClickObserver = Observer<Int> {
        when (it) {
            R.id.btnPayNow -> {
                launchActivity<PayBillMainActivity>(requestCode = RequestCodes.REQUEST_PAY_BILL) {
                    putExtra(ExtraKeys.SELECTED_BILL.name, viewModel.parentViewModel?.selectedBill)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_PAY_BILL -> {
                    setIntentResult()
                }
            }
        }
    }

    private fun setIntentResult() {
        val intent = Intent()
        requireActivity().setResult(Activity.RESULT_OK, intent)
        requireActivity().finish()
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
