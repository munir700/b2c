package co.yap.yapcore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import co.yap.yapcore.dagger.base.BaseViewModelFragment

abstract class BaseBindingFragment<V : IBase.ViewModel<*>> : BaseFragment<V>() {

    lateinit var viewDataBinding: ViewDataBinding
    /**
     * Indicates whether the current [BaseBindingFragment]'s content view is initialized or not.
     */
    var isViewCreated = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        // dependencies will be injected only once (based on the state of the content view)
        if(!isViewCreated) {
            injectDependencies()
        }
        super.onCreate(savedInstanceState)
        preInit()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wasViewCreated = isViewCreated
        isViewCreated = true

        // performing the initialization only in cases when the view was created for the first time
        if(!wasViewCreated) {
            init(savedInstanceState)
            postInit()
        }
        performDataBinding(savedInstanceState)

    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        //viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    /**MV
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

    /**
     * Get's called when it's the right time for you to initialize the UI elements.
     *
     * @param savedInstanceState the state bundle brought from the [Fragment.onViewCreated]
     */
    protected open fun init(savedInstanceState : Bundle?) {
        //
    }


    /**
     * Gets called right after the UI initialization.
     */
    protected open fun postInit() {
        //
    }
    /**
     * Gets called when it's the right time for you to inject the dependencies.
     */
    open fun injectDependencies() {
        //
    }
    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit() {
        //
    }

}