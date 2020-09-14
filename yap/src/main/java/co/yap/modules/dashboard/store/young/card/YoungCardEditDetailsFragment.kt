package co.yap.modules.dashboard.store.young.card

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungCardEditDetailsBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import com.google.android.material.tabs.TabLayout

class YoungCardEditDetailsFragment:
    BaseNavViewModelFragment<FragmentYoungCardEditDetailsBinding, IYoungCardEditDetails.State, YoungCardEditDetailsVM>(),
    TabLayout.OnTabSelectedListener {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_young_card_edit_details
    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }
}