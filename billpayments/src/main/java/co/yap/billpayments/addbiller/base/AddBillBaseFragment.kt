package co.yap.billpayments.addbiller.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.addbiller.main.AddBillViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class AddBillBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is AddBillBaseViewModel<*> && activity != null) {
            (viewModel as AddBillBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(AddBillViewModel::class.java)
        }
    }
}
