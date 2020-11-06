package co.yap.modules.dashboard.yapit.sendmoney.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class SendMoneyBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is SendMoneyBaseViewMode<*>) {
            (viewModel as SendMoneyBaseViewMode<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(SendMoneyMainViewModel::class.java)
        }
    }
}