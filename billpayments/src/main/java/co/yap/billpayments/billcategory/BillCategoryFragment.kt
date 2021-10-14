package co.yap.billpayments.billcategory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addbiller.main.AddBillActivity
import co.yap.billpayments.base.BillDashboardBaseFragment
import co.yap.billpayments.paybill.main.PayBillMainActivity
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener


class BillCategoryFragment : BillDashboardBaseFragment<IBillCategory.ViewModel>(),
    IBillCategory.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: BillCategoryViewModel
        get() = ViewModelProviders.of(this).get(BillCategoryViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.fragment_bill_category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.adapter.setItemListener(categoryItemListener)
    }

    private val categoryItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            onCategorySelection(data as BillProviderModel)
        }
    }

    private fun onCategorySelection(billCategory: BillProviderModel?) {
        if (!billCategory?.categoryType.equals("CREDIT_CARD")) {
            launchActivity<AddBillActivity>(requestCode = RequestCodes.REQUEST_ADD_BILL) {
                putExtra(
                    ExtraKeys.BILL_PROVIDER.name,
                    billCategory
                )
            }
        }
    }

    private fun startPayBillFlow(viewBillModel: ViewBillModel, pos: Int = 0) {
        launchActivity<PayBillMainActivity>(requestCode = RequestCodes.REQUEST_PAY_BILL) {
            putExtra(ExtraKeys.SELECTED_BILL.name, viewBillModel)
            putExtra(ExtraKeys.SELECTED_POSITION.name, pos)
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_ADD_BILL -> {
                    handleAddBillResult(data)
                }
                RequestCodes.REQUEST_PAY_BILL -> {
                    navigateBack(R.id.billsDashboardFragment)
                    viewModel.parentViewModel?.getViewBills()
                }
            }
        } else {
            when (requestCode) {
                RequestCodes.REQUEST_PAY_BILL -> {
                    navigateBack()
                    viewModel.parentViewModel?.getViewBills()
                }
            }
        }
    }

    private fun handleAddBillResult(data: Intent?) {
        val isSkipPayFlow = data?.getValue(
            ExtraKeys.IS_SKIP_PAY_BILL.name,
            ExtraType.BOOLEAN.name
        ) as Boolean

        val viewBillModel = data.getValue(
            ExtraKeys.SELECTED_BILL.name,
            ExtraType.PARCEABLE.name
        ) as? ViewBillModel

        if (isSkipPayFlow) {
            navigateBack()
            viewModel.parentViewModel?.getViewBills()
        } else {
            viewBillModel?.let {
                startPayBillFlow(viewBillModel)
            } ?: viewModel.parentViewModel?.getViewBills()
        }
    }
}
