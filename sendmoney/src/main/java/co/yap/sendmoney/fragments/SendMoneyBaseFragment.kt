package co.yap.sendmoney.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.sendmoney.viewmodels.SendMoneyViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class SendMoneyBaseFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> : BaseBindingFragment<VB,V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is SendMoneyBaseViewModel<*> && activity != null) {
            (viewModel as SendMoneyBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(requireActivity()).get(SendMoneyViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

}