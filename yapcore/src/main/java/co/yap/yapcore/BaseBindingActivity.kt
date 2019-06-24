package co.yap.yapcore

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity : BaseActivity() {

    private lateinit var viewDataBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }


    protected fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */

    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */

    @LayoutRes
    abstract fun getLayoutId(): Int

}