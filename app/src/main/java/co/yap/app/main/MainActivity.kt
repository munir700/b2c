package co.yap.app.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.app.R
import co.yap.app.YAPApplication
import co.yap.app.databinding.ActivityMainBinding
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.firebase.getFCMToken
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import co.yap.yapcore.managers.SessionManager
import com.adjust.sdk.Adjust
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.yap.core.utils.KEY_IS_USER_LOGGED_IN

class MainActivity : BaseBindingActivity<ActivityMainBinding, IMain.ViewModel>(), INavigator,
    IFragmentHolder {

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: IMain.ViewModel
        get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@MainActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.crashlytics.setUserId(
            SharedPreferenceManager.getInstance(this).getValueString(
                Constants.KEY_APP_UUID
            ) ?: ""
        )
        Firebase.crashlytics.setCustomKey(KEY_IS_USER_LOGGED_IN,false)
        YAPApplication.AUTO_RESTART_APP = false
        SessionManager.expireUserSession()
        launch {
            getFCMToken {
                Adjust.setPushToken(it, applicationContext)
            }
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

