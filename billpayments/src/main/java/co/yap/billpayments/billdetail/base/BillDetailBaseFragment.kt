package co.yap.billpayments.billdetail.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import co.yap.billpayments.billdetail.BillDetailViewModel
import co.yap.yapcore.BaseBindingFragmentV2
import co.yap.yapcore.IBase

abstract class BillDetailBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragmentV2<VB, V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            if (viewModel is BillDetailBaseViewModel<*>) {
                (viewModel as BillDetailBaseViewModel<*>).parentViewModel =
                    ViewModelProvider(it).get(BillDetailViewModel::class.java)
            }
        }
    }
}
