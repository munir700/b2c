package co.yap.household.onboarding

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.interfaces.IOnboarding
import co.yap.household.onboarding.viewmodels.OnboardingHouseHoldViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class OnboardingHouseHoldActivity : BaseBindingActivity<IOnboarding.ViewModel>(), INavigator,
    IFragmentHolder {

    override fun getBindingVariable(): Int = BR.viewModelHouseHold

    override fun getLayoutId(): Int = R.layout.activity_main_house_hold

    override val viewModel: IOnboarding.ViewModel
        get() = ViewModelProviders.of(this).get(OnboardingHouseHoldViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@OnboardingHouseHoldActivity, R.id.main_nav_host_container_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onboardingData.accountType = "B2C_ACCOUNT"
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()

//         SharedPreferenceManager(applicationContext).setThemeValue(R.style.AppTheme)
//        SharedPreferenceManager(applicationContext).setThemeValue(R.style.AppThemeHouseHold)

//        SharedPreferenceManager(applicationContext).getThemeValue()
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById( R.id.main_nav_host_container_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}