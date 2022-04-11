package co.yap.modules.dashboard.store.cardplans.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansBaseViewModel
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansMainViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class CardPlansBaseFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB,V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is CardPlansBaseViewModel<*>) {
            (viewModel as CardPlansBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(requireActivity()).get(CardPlansMainViewModel::class.java)
        }
    }
}