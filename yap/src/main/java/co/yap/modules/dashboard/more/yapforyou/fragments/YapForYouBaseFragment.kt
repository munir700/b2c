package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YapForYouBaseViewModel
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YapForYouMainViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class YapForYouBaseFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is YapForYouBaseViewModel<*>) {
            (viewModel as YapForYouBaseViewModel<*>).parentViewModel =
                ViewModelProvider(requireActivity()).get(YapForYouMainViewModel::class.java)
        }
    }

}