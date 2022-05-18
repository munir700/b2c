package co.yap.modules.dashboard.store.young.benefits

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungBenefitsBinding
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import co.yap.translation.Strings
import co.yap.widgets.SpacesItemDecoration
import co.yap.yapcore.dagger.base.interfaces.ManageToolBarListener
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.hilt.base.fragment.BaseRecyclerViewFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoungBenefitsFragment :
    BaseRecyclerViewFragmentV2<FragmentYoungBenefitsBinding, IYoungBenefits.State, YoungBenefitsVM, YoungBenefitsAdapter, YoungBenefitsModel>() {
    override fun getBindingVariable() = BR.youngBenefitsVM
    override fun getLayoutId() = R.layout.fragment_young_benefits
    override val viewModel: YoungBenefitsVM  by viewModels()

    override fun setHomeAsUpIndicator() = co.yap.yapcore.R.drawable.ic_back_arrow_left
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() =
        getString(Strings.screen_yap_young_onboarding_landing_button_text)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        if (activity is ManageToolBarListener) {
            (activity as ManageToolBarListener).setupToolbar(activity?.findViewById(R.id.toolBar))
        }
        super.postExecutePendingBindings(savedInstanceState)
        setRefreshEnabled(false)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val marginLeft = dimen(co.yap.yapcore.R.dimen.margin_normal)
        val marginTop = dimen(co.yap.yapcore.R.dimen.margin_normal_large)
        return SpacesItemDecoration(marginLeft, marginLeft, marginTop, marginTop, true)
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.cbSelectPlan -> {
                navigate(YoungBenefitsFragmentDirections.actionYoungBenefitsFragmentToYoungConfirmRelationshipFragment())
            }
        }
    }
}
