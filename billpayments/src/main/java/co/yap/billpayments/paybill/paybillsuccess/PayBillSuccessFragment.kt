package co.yap.billpayments.paybill.paybillsuccess

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPayBillSuccessBinding
import co.yap.billpayments.paybill.base.PayBillMainBaseFragment
import co.yap.yapcore.helpers.extentions.strikeThroughText

class PayBillSuccessFragment : PayBillMainBaseFragment<IPayBillSuccess.ViewModel>(),
    IPayBillSuccess.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bill_success
    override val viewModel: IPayBillSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(PayBillSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().tvDueAmount.strikeThroughText(!viewModel.state.isSuccessful.get())
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickListener)
    }

    private val clickListener = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> {
                setIntentResult()
            }
        }
    }

    override fun onBackPressed(): Boolean = true

    private fun setIntentResult() {
        val intent = Intent()
        requireActivity().setResult(Activity.RESULT_OK, intent)
        requireActivity().finish()
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getViewBinding(): FragmentPayBillSuccessBinding {
        return viewDataBinding as FragmentPayBillSuccessBinding
    }
}
