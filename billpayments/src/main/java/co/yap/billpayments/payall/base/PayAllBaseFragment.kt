package co.yap.billpayments.payall.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import co.yap.billpayments.payall.main.PayAllMainViewModel
import co.yap.yapcore.BaseBindingFragmentV2
import co.yap.yapcore.IBase

abstract class PayAllBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragmentV2<VB, V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            if (viewModel is PayAllBaseViewModel<*>) {
                (viewModel as PayAllBaseViewModel<*>).parentViewModel =
                    ViewModelProvider(it).get(PayAllMainViewModel::class.java)
            }
        }
    }
}
