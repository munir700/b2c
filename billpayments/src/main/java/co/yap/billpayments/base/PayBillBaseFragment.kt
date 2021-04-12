package co.yap.billpayments.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.dashboard.BillPaymentsViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class PayBillBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is PayBillBaseViewModel<*> && activity != null) {
            (viewModel as PayBillBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(BillPaymentsViewModel::class.java)
        }
    }
}