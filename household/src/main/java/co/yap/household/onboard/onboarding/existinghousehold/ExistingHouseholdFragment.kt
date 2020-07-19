package co.yap.household.onboard.onboarding.existinghousehold

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.householdsuccess.HouseHoldSuccessFragment
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toCamelCase
@Deprecated("")
class ExistingHouseholdFragment : BaseBindingFragment<IExistingHouseHold.ViewModel>(){

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

        if(it == AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING.name) {
            val bundle = Bundle()
//                bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, existingUser)
            accountInfo?.let {accInfo ->
                accInfo.notificationStatuses = it
                bundle.putParcelable(OnBoardingHouseHoldActivity.USER_INFO, accInfo)
            }

            startFragment(HouseHoldSuccessFragment::class.java.name, true, bundle)
        }else if(it == AccountStatus.INVITE_DECLINED.name){
            gotoYapDashboard()
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnAccept -> {
                // API Call for Accept
                viewModel.subAccountInvitationStatus((AccountStatus.INVITE_ACCEPTED.name.toCamelCase()))
            }

            R.id.tvOnBoardingExistingDeclineRequest -> {
                // API Call for Decline
                viewModel.subAccountInvitationStatus(AccountStatus.INVITE_DECLINED.name.toCamelCase())

            }
        }
    }

    private fun gotoYapDashboard() {
        launchActivity<YapDashboardActivity> {  }
        activity?.finish()
    }
}
