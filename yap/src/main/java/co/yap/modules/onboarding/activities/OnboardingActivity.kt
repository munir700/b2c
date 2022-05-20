package co.yap.modules.onboarding.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityOnboardingNavigationBinding
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IOnboarding
import co.yap.modules.onboarding.models.OnboardingData
import co.yap.modules.onboarding.viewmodels.OnboardingViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class OnboardingActivity :
    BaseBindingActivity<ActivityOnboardingNavigationBinding, IOnboarding.ViewModel>(), INavigator,
    IFragmentHolder {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_onboarding_navigation

    override val viewModel: IOnboarding.ViewModel
        get() = ViewModelProviders.of(this).get(OnboardingViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@OnboardingActivity, R.id.my_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun getIntentData() {
        viewModel.onboardingData.accountType = AccountType.B2C_ACCOUNT
        if (intent.hasExtra(ExtraKeys.ON_BOARDING_DATA.name)){
            viewModel.onboardingData =
                intent.getSerializableExtra(ExtraKeys.ON_BOARDING_DATA.name) as OnboardingData
        }
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onBackPressed() {
        if (viewModel.state.emailError) {
            trackEventWithScreenName(FirebaseEvent.SIGNUP_EMAIL_FAILURE)
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}