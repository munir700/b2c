package co.yap.sendMoney.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class SendMoneyBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is SendMoneyBaseViewModel<*> && activity != null) {
            (viewModel as SendMoneyBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(SendMoneyViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}