package co.yap.modules.onboarding.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import co.yap.R
import co.yap.modules.onboarding.adapters.WelcomePagerAdapter
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseActivity

class WelcomeActivity : BaseActivity<IWelcome.ViewModel>(), IWelcome.View {
    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter =
            WelcomePagerAdapter(context = this, layout = R.layout.screen_onboarding_welcome, contents = viewModel.pages)
        findViewById<ViewPager>(R.id.welcome_pager).adapter = adapter
    }
}