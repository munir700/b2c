package co.yap.billpayments.paybill

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.databinding.FragmentPayBillBinding

class PayBillFragment : PayBillBaseFragment<IPayBill.ViewModel>(),
    IPayBill.View, CompoundButton.OnCheckedChangeListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bill

    override val viewModel: PayBillViewModel
        get() = ViewModelProviders.of(this).get(PayBillViewModel::class.java)
    private val vsAutoPayment: ViewStub by lazy {
        getViewBinding().root.findViewById<ViewStub>(R.id.vsAutoPayment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().swAutoPayment.setOnCheckedChangeListener(this)
        initViewStub()
    }


    override fun initViewStub() {
        vsAutoPayment.layoutResource =
            R.layout.content_pay_bill_auto_payment
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.cDay -> {
                showToast("cDay clicked")
            }
            R.id.cWeek -> {
                showToast("cWeek clicked")

            }
            R.id.cMonth -> {
                showToast("cMonth clicked")

            }
        }
    }


    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.swAutoPayment -> {
                viewModel.state.isAutoPaymentOn = isChecked
                if (isChecked) {
                    vsAutoPayment.visibility = View.VISIBLE
                } else {
                    vsAutoPayment.visibility = View.GONE
                }
            }
        }
    }

    override fun getViewBinding(): FragmentPayBillBinding {
        return viewDataBinding as FragmentPayBillBinding
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}