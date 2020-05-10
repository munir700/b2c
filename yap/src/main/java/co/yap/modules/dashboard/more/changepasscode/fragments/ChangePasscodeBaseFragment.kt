package co.yap.modules.dashboard.more.changepasscode.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.dashboard.more.changepasscode.viewmodels.ChangePassCodeBaseViewModel
import co.yap.modules.dashboard.more.changepasscode.viewmodels.ChangePassCodeViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class ChangePasscodeBaseFragment<V : IBase.ViewModel<*>> :
    BaseBindingFragment<V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is ChangePassCodeBaseViewModel<*> && activity != null) {
            (viewModel as ChangePassCodeBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(requireActivity())
                    .get(ChangePassCodeViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}