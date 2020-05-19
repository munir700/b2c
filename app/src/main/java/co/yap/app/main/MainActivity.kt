package co.yap.app.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.app.R
import co.yap.app.YAPApplication
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class MainActivity : BaseBindingActivity<IMain.ViewModel>(), INavigator, IFragmentHolder {

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: IMain.ViewModel
        get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    private external fun signatureKeysFromJNI(): String

    init {
        System.loadLibrary("native-lib")
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@MainActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YAPApplication.AUTO_RESTART_APP = false
        //showToast(signatureKeysFromJNI())

//        val originalSign = AppSignature(
//            sha1 = "BE:61:41:5C:04:34:DE:D8:45:6E:C0:09:E0:95:DF:E4:49:80:A7:F6",
//            md5 = "8E:9C:B9:BC:0C:EA:BF:56:81:04:CD:16:15:B9:D0:62",
//            sha256 = "A5:B4:2F:1D:4A:E5:CA:CB:8D:83:5B:CE:1D:85:7A:76:41:FE:5C:B2:59:FB:3D:04:53:11:C8:BB:80:F1:66:FD"
//        )
//
//        SecurityHelper(this, originalSign, object : SignatureValidator {
//            override fun onValidate(isValid: Boolean) {
//                if (!isValid) {
//                    showToast("App signature not matched" + "^" + AlertType.DIALOG_WITH_FINISH)
//                }
//            }
//        })
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

