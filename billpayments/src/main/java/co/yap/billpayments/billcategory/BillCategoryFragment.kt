package co.yap.billpayments.billcategory

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.yapcore.enums.BillCategory


class BillCategoryFragment : PayBillBaseFragment<IBillCategory.ViewModel>(),
    IBillCategory.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: BillCategoryViewModel
        get() = ViewModelProviders.of(this).get(BillCategoryViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.fragment_bill_category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBillCategoriesApi()
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    val clickObserver = Observer<Int> {
        viewModel.parentViewModel?.selectedBillCategory = when (it) {
            R.id.includeCreditCard -> BillCategory.CREDIT_CARD
            R.id.includeTelecom -> BillCategory.TELECOM
            R.id.includeUtilities -> BillCategory.UTILITIES
            R.id.includeRTA -> BillCategory.RTA
            R.id.includeDubaiPolice -> BillCategory.DUBAI_POLICE
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
