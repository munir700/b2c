package co.yap.modules.subaccounts.account.dashboard

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountDashBoardBinding
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import javax.inject.Inject

class SubAccountDashBoardFragment :
    BaseNavViewModelFragment<FragmentSubAccountDashBoardBinding, ISubAccountDashBoard.State, SubAccountDashBoardVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    override fun getBindingVariable() = BR.subAccountDashBoardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_dash_board
    override fun getToolBarTitle(): String? {
        return "Household"
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.adapter.set(adapter)
    }

    override fun onClick(id: Int) {
    }
}
