package co.yap.billpayments.paybills

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment

class PayBillsFragment : PayBillBaseFragment<IPayBills.ViewModel>(),
    IPayBills.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bills

    override val viewModel: PayBillsViewModel
        get() = ViewModelProviders.of(this).get(PayBillsViewModel::class.java)

    private val vs: ViewStub by lazy {
        requireActivity().findViewById<ViewStub>(R.id.vsBillPayment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewStub()
    }

    private fun initViewStub() {
        vs.layoutResource = R.layout.layout_bill_payments_due
        vs.visibility = View.VISIBLE
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
            R.id.lAddBill->{
                navigate(R.id.action_payBillsFragment_to_billersFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}