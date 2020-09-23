package co.yap.modules.dashboard.store.young.subaccounts

import android.os.Bundle
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungSubaccountBinding
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import javax.inject.Inject

class YoungSubAccountsFragment :
    BaseNavViewModelFragment<FragmentYoungSubaccountBinding, IYoungSubAccounts.State, YoungSubAccountsVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_subaccount

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.adapter.set(adapter)
    }

    override fun onClick(id: Int) {
    }
}