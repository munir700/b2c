package co.yap.sendmoney.y2y.main.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.sendmoney.y2y.main.viewmodels.Y2YViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class Y2YBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is Y2YBaseViewModel<*>) {
            (viewModel as Y2YBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(Y2YViewModel::class.java)
        }
    }
}
