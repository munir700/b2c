package co.yap.modules.yapit.y2ytransfer.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.modules.yapit.y2ytransfer.viewmodels.Y2YViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class Y2YBaseFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is Y2YBaseViewModel<*>) {
            (viewModel as Y2YBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(Y2YViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()


    }

}
