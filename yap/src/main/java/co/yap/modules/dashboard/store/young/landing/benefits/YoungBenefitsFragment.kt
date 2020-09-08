package co.yap.modules.dashboard.store.young.landing.benefits

import android.os.Bundle
import android.view.View
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungBenefitsBinding
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsModel
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import androidx.lifecycle.Observer
class YoungBenefitsFragment :
    BaseRecyclerViewFragment<FragmentYoungBenefitsBinding, IYoungBenefits.State, YoungBenefitsVM, YoungBenefitsAdapter, YoungBenefitsModel>() {
    override fun getBindingVariable() = BR.youngBenefitsVM
    override fun getLayoutId() = R.layout.fragment_young_benefits
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }
    private fun onClick(it: Int?) {
    }
    override fun onItemClick(view: View, data: Any, pos: Int) {
        if (data is Store) {
            if (data.name == "YAP Young Benefits") {
                viewModel.clickEvent.setPayload(
                    SingleClickEvent.AdaptorPayLoadHolder(
                        view,
                        data,
                        pos
                    )
                )
                viewModel.clickEvent.setValue(view.id)
            }
        }
    }
    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
