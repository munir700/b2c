package co.yap.yapcore.hilt.base.activity

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import co.yap.app.YAPApplication
import co.yap.localization.LocaleManager
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.IBase
import co.yap.yapcore.helpers.extentions.initializeChatOverLayButton
import co.yap.yapcore.managers.isUserLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Safi ur Rehman
 */
abstract class BaseBindingActivityV2<V : IBase.ViewModel<*>> : BaseActivity<V>() {
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
        if (shouldShowChatChatOverLay() == true)
            initializeChatOverLayButton()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
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
}