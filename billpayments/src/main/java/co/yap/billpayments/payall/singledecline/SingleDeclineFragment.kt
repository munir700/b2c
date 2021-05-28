package co.yap.billpayments.payall.singledecline

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentSingleDeclineBinding
import co.yap.billpayments.payall.base.PayAllBaseFragment
import co.yap.yapcore.helpers.extentions.strikeThroughText

class SingleDeclineFragment : PayAllBaseFragment<ISingleDecline.ViewModel>(),
    ISingleDecline.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_single_decline
    private val singleDeclineViewModel: SingleDeclineViewModel by viewModels()
    override val viewModel: ISingleDecline.ViewModel
        get() = singleDeclineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().tvDueAmount.strikeThroughText(true)
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

    override fun getViewBinding(): FragmentSingleDeclineBinding {
        return viewDataBinding as FragmentSingleDeclineBinding
    }
}
