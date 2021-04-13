package co.yap.billpayments.billcategory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addBill.main.AddBillActivity
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.launchActivity


class BillCategoryFragment : PayBillBaseFragment<IBillCategory.ViewModel>(),
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
        viewModel.clickEvent.observe(this, clickObserver)
    }

    val clickObserver = Observer<Int> {
        when (it) {
            R.id.includeCreditCard -> onCategorySelection(viewModel.billcategories.get()?.get(0))
            R.id.includeTelecom -> onCategorySelection(viewModel.billcategories.get()?.get(1))
            R.id.includeUtilities -> onCategorySelection(viewModel.billcategories.get()?.get(2))
            R.id.includeRTA -> onCategorySelection(viewModel.billcategories.get()?.get(3))
            R.id.includeDubaiPolice -> onCategorySelection(viewModel.billcategories.get()?.get(4))
        }
    }

    private fun onCategorySelection(billCategory: BillProviderModel?) {
        launchActivity<AddBillActivity>(requestCode = RequestCodes.REQUEST_ADD_BILL) {
            putExtra(
                ExtraKeys.BILL_PROVIDER.name,
                billCategory
            )
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

                }
            }
        }
    }
}
