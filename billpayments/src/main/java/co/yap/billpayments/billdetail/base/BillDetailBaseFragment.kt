package co.yap.billpayments.billdetail.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.billdetail.BillDetailViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class BillDetailBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is BillDetailBaseViewModel<*> && activity != null) {
            (viewModel as BillDetailBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(BillDetailViewModel::class.java)
        }
    }
}
