package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IOnboarding
import co.yap.modules.onboarding.viewmodels.OnboardingViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class OnboardingActivity : BaseBindingActivity<IOnboarding.ViewModel>(), INavigator {
    companion object {

        private val ACCOUNT_TYPE = "account_type"

        fun newIntent(context: Context, accountType: AccountType): Intent {
            val intent = Intent(context, OnboardingActivity::class.java)
            intent.putExtra(ACCOUNT_TYPE, accountType)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onboardingData.accountType = getAccountType()
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
    }

    override val viewModel: IOnboarding.ViewModel
        get() = ViewModelProviders.of(this).get(OnboardingViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.my_nav_host_fragment)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_onboarding_navigation

    private fun getAccountType(): AccountType {
        return intent.getSerializableExtra(ACCOUNT_TYPE) as AccountType
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObserver(backButtonObserver)
        super.onDestroy()
    }

}