package co.yap.app.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.app.R
import co.yap.app.YAPApplication
import co.yap.security.AppSignature
import co.yap.security.SecurityHelper
import co.yap.security.SignatureValidator
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.config.BuildConfigManager
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class MainActivity : BaseBindingActivity<IMain.ViewModel>(), INavigator, IFragmentHolder {

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: IMain.ViewModel
        get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    private external fun signatureKeysFromJNI(name: String): AppSignature

    init {
        System.loadLibrary("native-lib")
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@MainActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YAPApplication.AUTO_RESTART_APP = false
        if (YAPApplication.configManager.isLiveRelease()) {
            val originalSign =
                signatureKeysFromJNI(
                    AppSignature::class.java.canonicalName?.replace(".", "/") ?: ""
                )

            SecurityHelper(this, originalSign, object : SignatureValidator {
                override fun onValidate(isValid: Boolean) {
                    if (!isValid) {
                        showToast("App signature not matched" + "^" + AlertType.DIALOG_WITH_FINISH)
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    fun onBackPressedDummy() {
        super.onBackPressed()
    }
}

