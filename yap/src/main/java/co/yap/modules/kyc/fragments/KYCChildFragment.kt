package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.kyc.viewmodels.DocumentsDashboardViewModel
import co.yap.modules.kyc.viewmodels.KYCChildViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class KYCChildFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> : BaseBindingFragment<VB,V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is KYCChildViewModel<*>) {
            (viewModel as KYCChildViewModel<*>).parentViewModel = activity?.let {
                ViewModelProviders.of(it).get(DocumentsDashboardViewModel::class.java)
            }

        }
    }
}