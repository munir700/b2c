package co.yap.app.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.app.modules.accountselection.activities.AccountSelectionActivity
import co.yap.yapcore.helpers.SharedPreferenceManager

class SplashActivity : BaseActivity<ISplash.ViewModel>(), ISplash.View {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override val viewModel: ISplash.ViewModel
        get() = ViewModelProviders.of(this).get(SplashViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.splashComplete.observe(this, Observer {
            finish()

            sharedPreferenceManager = SharedPreferenceManager(this@SplashActivity)

            //if (sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, false)) {
            //  startActivity(VerifyPasscodeActivity.newIntent(this, ""))
            //} else {
               startActivity(Intent(this, AccountSelectionActivity::class.java))
            //}
        })
    }
}
