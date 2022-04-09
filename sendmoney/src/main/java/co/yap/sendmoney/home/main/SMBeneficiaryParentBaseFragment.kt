package co.yap.sendmoney.home.main

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class SMBeneficiaryParentBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is SMBeneficiaryParentBaseViewModel<*>) {
            (viewModel as SMBeneficiaryParentBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(SMBeneficiaryParentViewModel::class.java)
        }
    }

}
