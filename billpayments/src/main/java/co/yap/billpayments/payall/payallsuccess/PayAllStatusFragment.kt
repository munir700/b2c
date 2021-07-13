package co.yap.billpayments.payall.payallsuccess

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.payall.base.PayAllBaseFragment

class PayAllStatusFragment : PayAllBaseFragment<IPayAllStatus.ViewModel>(),
    IPayAllStatus.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_all_status
    override val viewModel: IPayAllStatus.ViewModel
        get() = ViewModelProviders.of(this).get(PayAllStatusViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
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
}
