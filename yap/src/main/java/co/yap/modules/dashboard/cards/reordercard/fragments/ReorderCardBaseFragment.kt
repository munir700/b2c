package co.yap.modules.dashboard.cards.reordercard.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.dashboard.cards.reordercard.viewmodels.ReorderCardBaseViewModel
import co.yap.modules.dashboard.cards.reordercard.viewmodels.ReorderCardViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class ReorderCardBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> : BaseBindingFragment<VB,V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is ReorderCardBaseViewModel<*> && activity != null) {
            (viewModel as ReorderCardBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(ReorderCardViewModel::class.java)
        }
    }
}