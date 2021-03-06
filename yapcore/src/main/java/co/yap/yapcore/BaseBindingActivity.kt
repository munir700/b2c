package co.yap.yapcore

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import co.yap.app.YAPApplication
import co.yap.localization.LocaleManager
import co.yap.yapcore.helpers.extentions.getCountUnreadMessage
import co.yap.yapcore.helpers.extentions.initializeChatOverLayButton
import co.yap.yapcore.managers.isUserLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseBindingActivity<VB : ViewDataBinding, V : IBase.ViewModel<*>> : BaseActivity<V>() {

    open lateinit var viewDataBinding: VB
    /**
     * Indicates whether the current [BaseBindingFragment]'s content view is initialized or not.
     */
    var isViewCreated = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
//        if (!isViewCreated) {
//            preInit(savedInstanceState)
//        }
        preInit(savedInstanceState)
        // dependencies will be injected only once (based on the state of the content view)
        // For runtime permission handling if user
        // disable permission from settings manually
        if (YAPApplication.AUTO_RESTART_APP) {
            finishAffinity()
            restartApp()
        }
        super.onCreate(savedInstanceState)
        setupBindingView(this, getLayoutId()) {
            viewDataBinding = it
            performDataBinding(savedInstanceState)
        }
        if (shouldShowChatChatOverLay() == true)
            initializeChatOverLayButton()
    }
    private fun setupBindingView(
        fragmentActivity: FragmentActivity,
        layoutResId: Int,
        set: (VB) -> Unit
    ): VB {
        return DataBindingUtil.setContentView<VB>(fragmentActivity, layoutResId).also {
            set(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (shouldShowChatChatOverLay() == true)
            getCountUnreadMessage()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
        init(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
//        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        postInit()
        viewDataBinding.executePendingBindings()
        postExecutePendingBindings(savedInstanceState)
    }

    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit(savedInstanceState: Bundle?) {
        //
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
    protected open fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        //
    }

    fun launch(dispatcher: Dispatcher = Dispatcher.Main, block: suspend () -> Unit) {
        lifecycleScope.launch(
            when (dispatcher) {
                Dispatcher.Main -> Dispatchers.Main
                Dispatcher.Background -> Dispatchers.IO
                Dispatcher.LongOperation -> Dispatchers.Default
            }
        ) { block() }
    }

    open fun shouldShowChatChatOverLay() = isUserLogin()

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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }
    fun <VB : ViewDataBinding> getDataBindingView() = viewDataBinding as VB
}