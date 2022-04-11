package co.yap.modules.location.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.location.viewmodels.LocationSelectionBaseViewModel
import co.yap.modules.location.viewmodels.LocationViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class LocationBaseFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB,V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is LocationSelectionBaseViewModel<*> && activity != null) {
            (viewModel as LocationSelectionBaseViewModel<*>).parentViewModel =
                ViewModelProvider(requireActivity())
                    .get(LocationViewModel::class.java)
        }
    }

}