package co.yap.billpayments.paybills

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.databinding.FragmentPayBillsBinding
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel

class PayBillsFragment : PayBillBaseFragment<IPayBills.ViewModel>(),
    IPayBills.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bills

    override val viewModel: PayBillsViewModel
        get() = ViewModelProviders.of(this).get(PayBillsViewModel::class.java)

    private val vs: ViewStub by lazy {
        (viewDataBinding as FragmentPayBillsBinding).root.findViewById<ViewStub>(R.id.vsBillPayment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewStub(viewModel.state.showBillCategory)
    }

    private fun initViewStub(showBillCategory: Boolean) {
        if (vs.layoutResource == 0) {
            vs.layoutResource =
                if (showBillCategory)
                    R.layout.layout_bill_categories
                else
                    R.layout.layout_bill_payments_due
        }
        vs.visibility = View.VISIBLE
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
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

    override fun onStop() {
        super.onStop()
        vs.visibility = View.GONE
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