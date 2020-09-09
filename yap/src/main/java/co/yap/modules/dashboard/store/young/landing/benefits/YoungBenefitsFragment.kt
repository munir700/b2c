package co.yap.modules.dashboard.store.young.landing.benefits

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungBenefitsBinding
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment

class YoungBenefitsFragment :
    BaseRecyclerViewFragment<FragmentYoungBenefitsBinding, IYoungBenefits.State, YoungBenefitsVM, YoungBenefitsAdapter, YoungBenefitsModel>() {
    override fun getBindingVariable() = BR.youngBenefitsVM
    override fun getLayoutId() = R.layout.fragment_young_benefits
    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
