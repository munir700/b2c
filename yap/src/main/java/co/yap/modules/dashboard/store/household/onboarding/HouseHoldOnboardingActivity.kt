package co.yap.modules.dashboard.store.household.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IBaseOnboarding
import co.yap.modules.dashboard.store.household.onboarding.viewmodels.HouseHoldOnboardingViewModel
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class HouseHoldOnboardingActivity : BaseBindingActivity<IBaseOnboarding.ViewModel>(), INavigator,
    IFragmentHolder {


    companion object {
        private const val SELECTED_PLAN = "selected_plan"
        fun newIntent(context: Context, selectedPlan: HouseHoldPlan? = null): Intent {
            val intent = Intent(context, HouseHoldOnboardingActivity::class.java)
            intent.putExtra(SELECTED_PLAN, selectedPlan)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_house_hold_onboarding

    override val viewModel: IBaseOnboarding.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldOnboardingViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@HouseHoldOnboardingActivity,
            R.id.main_house_hold_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
        getIntentData()
    }

    private fun getIntentData() {
        if (intent != null && intent.hasExtra(SELECTED_PLAN)) {
            val householdPlan: HouseHoldPlan? =
                intent.getParcelableExtra<HouseHoldPlan?>(SELECTED_PLAN)
            if (householdPlan != null) {
                viewModel.selectedPlanType = householdPlan
            } else {
                showToast("Please Select Plan")
                finish()
            }
        } else {
            showToast("Please Select Plan")
            finish()
        }
    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()

    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }


    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_house_hold_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }

}

