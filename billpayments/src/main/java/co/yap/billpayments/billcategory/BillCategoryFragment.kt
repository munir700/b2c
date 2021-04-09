package co.yap.billpayments.billcategory

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment


class BillCategoryFragment : PayBillBaseFragment<IBillCategory.ViewModel>(),
    IBillCategory.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: BillCategoryViewModel
        get() = ViewModelProviders.of(this).get(BillCategoryViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.fragment_bill_category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBillCategories()
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    val clickObserver = Observer<Int> {
        viewModel.parentViewModel?.selectedBillProvider = when (it) {
            R.id.includeCreditCard -> viewModel.billcategories.get()?.get(0)
            R.id.includeTelecom -> viewModel.billcategories.get()?.get(1)
            R.id.includeUtilities -> viewModel.billcategories.get()?.get(2)
            R.id.includeRTA -> viewModel.billcategories.get()?.get(3)
            R.id.includeDubaiPolice -> viewModel.billcategories.get()?.get(4)
            else -> null
        }
        navigate(R.id.action_billCategoryFragment_to_billersFragment)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
