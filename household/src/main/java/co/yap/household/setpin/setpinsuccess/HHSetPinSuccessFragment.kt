package co.yap.household.setpin.setpinsuccess

import android.os.Bundle
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhSetPinSuccessBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.launchActivity

class HHSetPinSuccessFragment :
    BaseNavViewModelFragment<FragmentHhSetPinSuccessBinding, IHHSetPinSuccess.State, HHSetPinSuccessVM>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_success
    override fun toolBarVisibility(): Boolean? = false

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        trackAdjustPlatformEvent(AdjustEvents.HH_USER_ACCOUNT_ACTIVE.type)
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGoToDashboard -> {
                launchActivity<NavHostPresenterActivity>() {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                }
            }
        }
    }
}
