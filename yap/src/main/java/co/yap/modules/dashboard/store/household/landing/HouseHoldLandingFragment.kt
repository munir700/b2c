package co.yap.modules.dashboard.store.household.landing

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHouseHoldLandingBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent

class HouseHoldLandingFragment :
    BaseNavViewModelFragment<FragmentHouseHoldLandingBinding, IHouseHoldLanding.State, HouseHoldLandingVM>() {
    override fun getBindingVariable() = BR.houseHoldLandingVM
    override fun getLayoutId() = R.layout.fragment_house_hold_landing

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setupToolbar(toolbar = mViewDataBinding.toolbar, setActionBar = true) {
            finishActivity()
        }
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    override fun setHomeAsUpIndicator() = R.drawable.ic_close_white

    override fun toolBarVisibility() = false
    private fun onClick(id: Int) {
        when (id) {
            R.id.btnGetHouseHoldAccount -> {
                trackEvent(HHSubscriptionEvents.HH_START_SUBSCRIPTION.type)
                trackAdjustPlatformEvent(AdjustEvents.HOUSE_HOLD_MAIN_USER_SUBSCRIPTION.type)
                navigate(HouseHoldLandingFragmentDirections.actionHouseHoldLandingFragmentToSubscriptionSelectionFragment())
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
