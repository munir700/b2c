package co.yap.yapcore.defaults

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseFragment

open class DefaultFragment : BaseFragment<IDefault.ViewModel>() {
    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(DefaultViewModel::class.java)

    override fun performDataBinding(savedInstanceState: Bundle?) {

    }
}