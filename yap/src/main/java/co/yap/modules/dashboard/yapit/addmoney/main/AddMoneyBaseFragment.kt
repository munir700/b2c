package co.yap.modules.dashboard.yapit.addmoney.main

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class AddMoneyBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is AddMoneyBaseViewModel<*>) {
            (viewModel as AddMoneyBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(AddMoneyViewModel::class.java)
        }
    }
}
