package co.yap.yapcore

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.app.YAPApplication
import co.yap.localization.LocaleManager

abstract class BaseBindingActivity<V : IBase.ViewModel<*>> : BaseActivity<V>() {

    lateinit var viewDataBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // For runtime permission handling if user
        // disable permission from settings manually
        if (YAPApplication.AUTO_RESTART_APP) {
            finishAffinity()
            restartApp()
        }
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }

    private fun performDataBinding() {
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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }
}