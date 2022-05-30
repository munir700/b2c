package co.yap.household.onboarding.kycsuccess

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentKycSuccessBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents

import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KycSuccessFragment :
    BaseNavViewModelFragmentV2<FragmentKycSuccessBinding, IKycSuccess.State, KycSuccessVM>() {
    override fun setDisplayHomeAsUpEnabled() = false
    override fun toolBarVisibility() = false
    override fun getBindingVariable() = BR.kycSuccessVM

    override val viewModel: KycSuccessVM by viewModels()

    override fun getLayoutId() = R.layout.fragment_kyc_success

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        if (SessionManager.isExistingUser()) {
            trackAdjustPlatformEvent(AdjustEvents.HH_USER_EXISTING_ACCOUNT_ACTIVE.type)
        } else {
            trackAdjustPlatformEvent(AdjustEvents.ONBOARD_NEW_HH_USER_ONBOARDING_SUCCESS.type)
        }
        viewDataBinding.btnTopUp.setOnClickListener {
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

    override fun onClick(id: Int) {
    }
}
