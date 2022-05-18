package co.yap.modules.dashboard.store.young.subaccounts

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungSubaccountBinding
import co.yap.translation.Strings.screen_yap_young_landing_display_text_title
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_young_subaccount.*


@AndroidEntryPoint
class YoungSubAccountsFragment :
    BaseNavViewModelFragmentV2<FragmentYoungSubaccountBinding, IYoungSubAccounts.State, YoungSubAccountsVM>() {

    val adapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(requireActivity(), childFragmentManager)
    }

    override val viewModel: YoungSubAccountsVM by viewModels()

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_subaccount
    override fun getToolBarTitle() = getString(screen_yap_young_landing_display_text_title)
    override fun setDisplayHomeAsUpEnabled() = true
    //TODO uncomment this code when complete migrated
   /* override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewStub.inflate()
        viewModel.adapter.set(adapter)
    }*/

    override fun onClick(id: Int) {
    }
}