package co.yap.billpayments.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import co.yap.billpayments.dashboard.BillPaymentsViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class BillDashboardBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (viewModel as? BillDashboardBaseViewModel<*>)?.apply {
            parentViewModel =
                ViewModelProvider(requireActivity()).get(BillPaymentsViewModel::class.java)
        }
    }
}