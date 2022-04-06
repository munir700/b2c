package co.yap.app.main

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseBindingFragmentV2
import co.yap.yapcore.IBase

abstract class MainChildFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragmentV2<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is MainChildViewModel<*>) {
            activity?.let {
                (viewModel as MainChildViewModel<*>).parentViewModel =
                    ViewModelProviders.of(it).get(MainViewModel::class.java)
            }
        }
    }
}