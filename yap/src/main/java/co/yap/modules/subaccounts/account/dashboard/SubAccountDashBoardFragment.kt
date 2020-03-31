package co.yap.modules.subaccounts.account.dashboard

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountDashBoardBinding
import co.yap.modules.subaccounts.account.card.SubAccountCardFragment
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import javax.inject.Inject

class SubAccountDashBoardFragment :
    BaseViewModelFragment<FragmentSubAccountDashBoardBinding, ISubAccountDashBoard.State, SubAccountDashBoardVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    override fun getBindingVariable() = BR.subAccountDashBoardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_dash_board

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        adapter.addFragmentInfo<SubAccountCardFragment>()
        adapter.addFragmentInfo<SubAccountCardFragment>()
        viewModel.adapter.set(adapter)
    }
}