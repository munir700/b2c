package co.yap.modules.dashboard.store.young.landing.benefits

import android.os.Bundle
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungBenefitsBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungBenefitsFragment :
    BaseNavViewModelFragment<FragmentYoungBenefitsBinding, IYoungBenefits.State, YoungBenefitsVM>() {
    override fun getBindingVariable() = BR.youngBenefitsVM
    override fun getLayoutId() = R.layout.fragment_young_benefits
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setupToolbar(toolbar = mViewDataBinding.toolbar, setActionBar = true) {
            finishActivity()
        }
    }

    override fun getToolBarTitle(): String? {
        return "Get YAP Young"
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnGetHouseHoldAccount -> {
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
