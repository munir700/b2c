package co.yap.modules.dashboard.store.young.subaccounts

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungSubaccountBinding
import co.yap.translation.Strings.screen_yap_young_landing_display_text_title
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import kotlinx.android.synthetic.main.fragment_young_subaccount.*
import javax.inject.Inject


class YoungSubAccountsFragment :
    BaseNavViewModelFragment<FragmentYoungSubaccountBinding, IYoungSubAccounts.State, YoungSubAccountsVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_subaccount
    override fun getToolBarTitle() = getString(screen_yap_young_landing_display_text_title)
    override fun setDisplayHomeAsUpEnabled() = true
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewStub.inflate()
        viewModel.adapter.set(adapter)
    }

    override fun onClick(id: Int) {
    }
}