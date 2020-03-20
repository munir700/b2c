package co.yap.household.onboard.onboarding.existinghousehold

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.household.onboard.onboarding.householdsuccess.HouseHoldSuccessFragment
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.managers.MyUserManager

class ExistingHouseholdFragment : BaseBindingFragment<IExistingHouseHold.ViewModel>(),
    IFragmentHolder {

//    private var existingUser:Boolean = false
    private var accountInfo:AccountInfo? = null

    override fun getBindingVariable(): Int {
        return BR.existingHouseHoldViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_on_boarding_existing_yap
    }

    override val viewModel: IExistingHouseHold.ViewModel
        get() = ViewModelProviders.of(this).get(ExistingHouseholdViewModel::class.java)

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
        viewModel.subAccountInvitationStatus.observe(this, invitationStatusObserver)
    }

    private val invitationStatusObserver = Observer<String> {

        if(it == "INVITE_ACCEPTED") {
            val bundle = Bundle()
//                bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, existingUser)
            accountInfo?.let {
                bundle.putParcelable(OnBoardingHouseHoldActivity.USER_INFO, it)
            }
            startFragment(
                HouseHoldSuccessFragment::class.java.name,
                false,
                bundle,
                showToolBar = false
            )
        }else if(it == "INVITE_DECLINE"){
            gotoYapDashboard()
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnAccept -> {
                // API Call for Accept
                viewModel.subAccountInvitationStatus("INVITE_ACCEPTED")
            }

            R.id.tvOnBoardingExistingDeclineRequest -> {
                // API Call for Decline
                viewModel.subAccountInvitationStatus("INVITE_DECLINE")

            }
        }
    }

    private fun gotoYapDashboard() {
        startActivity(
            YapToYapDashboardActivity.getIntent(
                requireContext(),
                true,
                null
            )
        )
    }
}
