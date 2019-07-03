package co.yap.app.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.app.modules.accountselection.activities.AccountSelectionActivity

class SplashActivity : BaseActivity<ISplash.ViewModel>(), ISplash.View {

    override val viewModel: ISplash.ViewModel
        get() = ViewModelProviders.of(this).get(SplashViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.splashComplete.observe(this, Observer {
            finish()
            startActivity(Intent(this, AccountSelectionActivity::class.java))
        })
    }
}
