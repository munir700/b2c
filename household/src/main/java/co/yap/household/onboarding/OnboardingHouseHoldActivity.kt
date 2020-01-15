package co.yap.household.onboarding

import android.content.Context
import android.content.Intent
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
import co.yap.yapcore.enums.NotificationStatus
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class OnboardingHouseHoldActivity : BaseBindingActivity<IOnboarding.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        const val BUNDLE_DATA = "bundle_data"
        const val EXISTING_USER = "existingYapUser"
        const val USER_INFO = "user_info"
        fun getIntent(context: Context, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, OnboardingHouseHoldActivity::class.java)
            intent.putExtra(BUNDLE_DATA, bundle)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModelHouseHold

    override fun getLayoutId(): Int = R.layout.activity_main_house_hold

    override val viewModel: IOnboarding.ViewModel
        get() = ViewModelProviders.of(this).get(OnboardingHouseHoldViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@OnboardingHouseHoldActivity,
            R.id.main_nav_host_container_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.getBundleExtra(BUNDLE_DATA)?.let {
            viewModel.state.accountInfo = it.getParcelable(USER_INFO)
            viewModel.state.existingYapUser = it.getBoolean(EXISTING_USER, false)
        }
        viewModel.state.accountInfo?.run {
            if (!notificationStatuses.isNullOrBlank())
                when (NotificationStatus.valueOf(notificationStatuses)) {
                    NotificationStatus.PARNET_MOBILE_VERIFICATION_PENDING -> {
                    }
                    NotificationStatus.PASS_CODE_PENDING -> {
                    }
                    NotificationStatus.EMAIL_PENDING -> {
                    }
                    NotificationStatus.ON_BOARDED -> {
                    }
                    NotificationStatus.MEETING_SCHEDULED -> {
                    }
                    NotificationStatus.MEETING_SUCCESS -> {
                    }
                    NotificationStatus.MEETING_FAILED -> {
                    }
                    NotificationStatus.CARD_ACTIVATED -> {
                    }
                    else -> {
                        //findNavController().navigate(R.id.action_goto_yapDashboardActivity)
                    }
                }
        }
        viewModel.onboardingData.accountType = "B2C_ACCOUNT"
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