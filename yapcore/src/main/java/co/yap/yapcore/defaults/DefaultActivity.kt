package co.yap.yapcore.defaults

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseActivity

open class DefaultActivity : BaseActivity<IDefault.ViewModel>() {
    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(DefaultViewModel::class.java)

    override fun performDataBinding(savedInstanceState: Bundle?) {

    }
}