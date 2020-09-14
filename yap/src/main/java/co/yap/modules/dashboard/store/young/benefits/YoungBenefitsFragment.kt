package co.yap.modules.dashboard.store.young.benefits

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungBenefitsBinding
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment

class YoungBenefitsFragment :
    BaseRecyclerViewFragment<FragmentYoungBenefitsBinding, IYoungBenefits.State, YoungBenefitsVM, YoungBenefitsAdapter, YoungBenefitsModel>() {
    override fun getBindingVariable() = BR.youngBenefitsVM
    override fun getLayoutId() = R.layout.fragment_young_benefits

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setRefreshEnabled(false)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(it: Int?) {
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
