package co.yap.household.onboard.onboarding.householdsuccess

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.launchActivity

class HouseHoldSuccessFragment : BaseBindingFragment<IHouseHoldSuccess.ViewModel>() {
//    private var existingUser:Boolean = false
    private var accountInfo: AccountInfo? = null

    override fun getBindingVariable(): Int {
        return BR.houseHoldSuccessViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_onboarding_house_hold_success
    }

    override val viewModel: IHouseHoldSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(HouseholdSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            existingUser = it.getBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, false)
            accountInfo = it.getParcelable(OnBoardingHouseHoldActivity.USER_INFO)

            accountInfo?.let {
                viewModel.setUserData(it)
            }
        }
        addObservers()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnCompleteVerification -> {
                launchActivity<OnBoardingHouseHoldActivity> (clearPrevious = true) {
                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, accountInfo)
                }
            }

            R.id.tvSkipAndLater -> {
                startActivity(
                    Intent(
                        requireContext(),
                        YapDashboardActivity::class.java
                    )
                )
                activity?.finish()
            }
        }
    }


}
