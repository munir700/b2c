package co.yap.modules.dashboard.store.household.landing

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHouseHoldLandingBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class HouseHoldLandingFragment :
    BaseNavViewModelFragment<FragmentHouseHoldLandingBinding, IHouseHoldLanding.State, HouseHoldLandingVM>() {

    override fun getBindingVariable() = BR.houseHoldLandingVM
    override fun getLayoutId() = R.layout.fragment_house_hold_landing
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setupToolbar(toolbar = mViewDataBinding.toolbar) {

        }
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnGetHouseHoldAccount -> {
            }
        }
    }
}