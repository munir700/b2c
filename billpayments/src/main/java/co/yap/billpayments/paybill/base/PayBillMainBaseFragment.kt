package co.yap.billpayments.paybill.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.dashboard.BillPaymentsViewModel
import co.yap.billpayments.paybill.main.PayBillMainViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class PayBillMainBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is PayBillMainBaseViewModel<*> && activity != null) {
            (viewModel as PayBillMainBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(PayBillMainViewModel::class.java)
        }
    }
}