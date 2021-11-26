package co.yap.billpayments.payall.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.payall.main.PayAllMainViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class PayAllBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is PayAllBaseViewModel<*> && activity != null) {
            (viewModel as PayAllBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(PayAllMainViewModel::class.java)
        }
    }
}
