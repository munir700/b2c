package co.yap.yapcore

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.app.YAPApplication

abstract class BaseBindingActivity<V : IBase.ViewModel<*>> : BaseActivity<V>() {

    open lateinit var viewDataBinding: ViewDataBinding

    /**
     * Indicates whether the current [BaseBindingActivity]'s content view is initialized or not.
     */
    var isViewCreated = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        preInit(savedInstanceState)
        // dependencies will be injected only once (based on the state of the content view)
        if (!isViewCreated) {
            injectDependencies()
        }
        // For runtime permission handling if user
        // disable permission from settings manually
        if (YAPApplication.AUTO_RESTART_APP) {
            finishAffinity()
            restartApp()
        }
        super.onCreate(savedInstanceState)

        performDataBinding(savedInstanceState)
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
        init(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        postInit()
        viewDataBinding.executePendingBindings()
        postExecutePendingBindings()
    }

    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit(savedInstanceState: Bundle?) {
        //
    }

    /**
     * Gets called when it's the right time for you to inject the dependencies.
     */
    open fun injectDependencies() {
    }

    /**
     * Get's called when it's the right time for you to initialize the UI elements.
     *
     * @param savedInstanceState state bundle brought from the [android.app.Activity.onCreate]
     */
    protected open fun init(savedInstanceState: Bundle?) {
        //
    }


    /**
     * Gets called right after the UI initialization.
     */
    protected open fun postInit() {
        //
    }

    /**
     * Gets called right after the UI executePendingBindings.
     */
    protected open fun postExecutePendingBindings() {
        //
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