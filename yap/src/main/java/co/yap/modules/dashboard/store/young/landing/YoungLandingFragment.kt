package co.yap.modules.dashboard.store.young.landing

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungLandingBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungLandingFragment :
    BaseNavViewModelFragment<FragmentYoungLandingBinding, IYoungLanding.State, YoungLandingVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_landing

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
                navigate(YoungLandingFragmentDirections.actionYoungLandingFragmentToYoungBenefitsFragment())
            }
        }
    }
}
