package co.yap.billpayments.paybills

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel

class PayBillsFragment : PayBillBaseFragment<IPayBills.ViewModel>(),
    IPayBills.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bills

    override val viewModel: PayBillsViewModel
        get() = ViewModelProviders.of(this).get(PayBillsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.lMyBills -> {
                navigate(R.id.action_payBillsFragment_to_myBillsFragment)
            }
            R.id.lAnalytics -> {
            }
            R.id.lAddBill -> navigate(R.id.action_payBillsFragment_to_addBillFragment)
            R.id.includeCreditCard -> onCategorySelection(viewModel.billcategories.get()?.get(0))
            R.id.includeTelecom -> onCategorySelection(viewModel.billcategories.get()?.get(1))
            R.id.includeUtilities -> onCategorySelection(viewModel.billcategories.get()?.get(2))
            R.id.includeRTA -> onCategorySelection(viewModel.billcategories.get()?.get(3))
            R.id.includeDubaiPolice -> onCategorySelection(viewModel.billcategories.get()?.get(4))
        }
    }

    private fun onCategorySelection(billCategory: BillProviderModel?) {
        viewModel.parentViewModel?.selectedBillProvider = billCategory
        navigate(R.id.action_payBillsFragment_to_billersFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
