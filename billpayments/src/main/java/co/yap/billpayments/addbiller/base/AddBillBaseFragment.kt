package co.yap.billpayments.addbiller.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import co.yap.billpayments.addbiller.main.AddBillViewModel
import co.yap.yapcore.BaseBindingFragmentV2
import co.yap.yapcore.IBase

abstract class AddBillBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragmentV2<VB, V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { activity ->
            if (viewModel is AddBillBaseViewModel<*>) {
                (viewModel as AddBillBaseViewModel<*>).parentViewModel =
                    ViewModelProvider(activity).get(AddBillViewModel::class.java)
            }
        }
    }
}
