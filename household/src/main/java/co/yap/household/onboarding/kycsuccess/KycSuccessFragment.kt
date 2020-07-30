package co.yap.household.onboarding.kycsuccess

import android.os.Bundle
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentKycSuccessBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_kyc_success.*

class KycSuccessFragment :
    BaseNavViewModelFragment<FragmentKycSuccessBinding, IKycSuccess.State, KycSuccessVM>() {
    override fun setDisplayHomeAsUpEnabled() = false
    override fun toolBarVisibility() = false
    override fun getBindingVariable() = BR.kycSuccessVM

    override fun getLayoutId() = R.layout.fragment_kyc_success

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        if (MyUserManager.isExistingUser()) {
            trackAdjustPlatformEvent(AdjustEvents.HH_USER_EXISTING_ACCOUNT_ACTIVE.type)
        } else {
            trackAdjustPlatformEvent(AdjustEvents.ONBOARD_NEW_HH_USER_ONBOARDING_SUCCESS.type)
        }
        btnTopUp.setOnClickListener {
            trackAdjustPlatformEvent(AdjustEvents.KYC_END.type)
            launchActivity<NavHostPresenterActivity>(clearPrevious = true) {
                putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                putExtra(
                    NAVIGATION_Graph_START_DESTINATION_ID,
                    R.id.householdDashboardFragment
                )
            }
        }
    }
}
