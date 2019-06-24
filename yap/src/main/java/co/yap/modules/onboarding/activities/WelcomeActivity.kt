package co.yap.modules.onboarding.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseActivity

class WelcomeActivity : BaseActivity<IWelcome.ViewModel>(), IWelcome.View {
    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.handlePressOnGetStarted()
    }
}