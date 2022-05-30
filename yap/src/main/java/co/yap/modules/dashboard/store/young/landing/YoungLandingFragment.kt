package co.yap.modules.dashboard.store.young.landing

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungLandingBinding
import co.yap.modules.dashboard.store.household.landing.HouseHoldLandingFragmentDirections

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoungLandingFragment :
    BaseNavViewModelFragmentV2<FragmentYoungLandingBinding, IYoungLanding.State, YoungLandingVM>() {
    override fun getBindingVariable() = BR.viewModel

    override val viewModel: YoungLandingVM by viewModels()


    override fun getLayoutId() = R.layout.fragment_young_landing
    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGetHouseHoldAccount -> {
                navigate(YoungLandingFragmentDirections.actionYoungLandingFragmentToYoungBenefitsFragment())
            }
        }    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setupToolbar(toolbar = viewDataBinding.toolbar, setActionBar = true) {
            finishActivity()
        }
    }

    override fun setHomeAsUpIndicator() = R.drawable.ic_close_white
    override fun toolBarVisibility() = false


}
