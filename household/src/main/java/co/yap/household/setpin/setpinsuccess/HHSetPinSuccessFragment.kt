package co.yap.household.setpin.setpinsuccess

import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhSetPinSuccessBinding
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity

class HHSetPinSuccessFragment :
    BaseNavViewModelFragment<FragmentHhSetPinSuccessBinding, IHHSetPinSuccess.State, HHSetPinSuccessVM>() {
    override fun getBindingVariable(): Int =BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_success
    override fun toolBarVisibility(): Boolean? = false

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.clickEvent.observe(this, clickEvent)
        trackAdjustPlatformEvent(AdjustEvents.HH_USER_ACCOUNT_ACTIVE.type)
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> {
                launchActivity<HouseHoldLandingActivity>(clearPrevious = true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObservers(this)
    }
}