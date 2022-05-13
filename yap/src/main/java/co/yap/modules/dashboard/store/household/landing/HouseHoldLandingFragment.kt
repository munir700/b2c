package co.yap.modules.dashboard.store.household.landing

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHouseHoldLandingBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HouseHoldLandingFragment :
    BaseNavViewModelFragmentV2<FragmentHouseHoldLandingBinding, IHouseHoldLanding.State, HouseHoldLandingVM>() {
    override fun getBindingVariable() = BR.houseHoldLandingVM
    override fun getLayoutId() = R.layout.fragment_house_hold_landing
    override val viewModel: HouseHoldLandingVM by viewModels()

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setupToolbar(toolbar = mViewDataBinding.toolbar, setActionBar = true) {
            finishActivity()
        }
    }

    override fun setHomeAsUpIndicator() = R.drawable.ic_close_white
    override fun toolBarVisibility() = false
    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGetHouseHoldAccount -> {
                trackEvent(HHSubscriptionEvents.HH_START_SUBSCRIPTION.type)
                trackAdjustPlatformEvent(AdjustEvents.HOUSE_HOLD_MAIN_USER_SUBSCRIPTION.type)
                navigate(HouseHoldLandingFragmentDirections.actionHouseHoldLandingFragmentToSubscriptionSelectionFragment())
            }
        }
    }


}
