package co.yap.household.onboard.onboarding.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.main.interfaces.IOnboarding
import co.yap.household.onboard.onboarding.main.viewmodels.OnboardingHouseHoldViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
@Deprecated("use co.yap.household.onboarding.main.OnBoardingHouseHoldActivity")
class OnBoardingHouseHoldActivity : BaseBindingActivity<IOnboarding.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        const val EXISTING_USER = "existingYapUser"
        const val USER_INFO = "user_info"
        const val NEXT_SCREEN = "next_screen"
    }

    override fun getBindingVariable(): Int = BR.viewModelHouseHold

    override fun getLayoutId(): Int = R.layout.activity_main_house_hold

    override val viewModel: IOnboarding.ViewModel
        get() = ViewModelProviders.of(this).get(OnboardingHouseHoldViewModel::class.java)
    val finalHost = NavHostFragment.create(R.navigation.hh_sub_account_onboarding_navigation)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@OnBoardingHouseHoldActivity,
            R.id.main_nav_host_container_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.accountInfo = intent.getParcelableExtra(USER_INFO)
        viewModel.state.existingYapUser = intent.getBooleanExtra(EXISTING_USER, false)
        viewModel.state.nextScreen = intent.getBooleanExtra(NEXT_SCREEN, false)

        viewModel.onboardingData.accountType = AccountType.B2C_HOUSEHOLD.name
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)

    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_container_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}