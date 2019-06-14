package co.yap.yapcore

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity : BaseActivity() {

    protected lateinit var viewDataBinding: ViewDataBinding
    protected lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }


    protected fun performDataBinding() {
        viewDataBinding = DataBindingUtil
            .setContentView<ViewDataBinding>(this, getLayoutId())
        this.baseViewModel = if (baseViewModel == null) getViewModel() else baseViewModel
        viewDataBinding.setVariable(getBindingVariable(), baseViewModel)
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