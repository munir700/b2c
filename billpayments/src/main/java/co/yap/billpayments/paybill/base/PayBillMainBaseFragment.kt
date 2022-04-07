package co.yap.billpayments.paybill.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import co.yap.billpayments.paybill.main.PayBillMainViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class PayBillMainBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { owner ->
            if (viewModel is PayBillMainBaseViewModel<*>) {
                (viewModel as PayBillMainBaseViewModel<*>).parentViewModel =
                    ViewModelProvider(owner).get(PayBillMainViewModel::class.java)
            }
        }
    }
}