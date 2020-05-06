package co.yap.modules.subaccounts.householdsetpin.hhsetpinsuccess

import androidx.lifecycle.Observer
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentHhSetPinSuccessBinding
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
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