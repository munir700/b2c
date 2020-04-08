package co.yap.modules.subaccounts.account.dashboard

import android.view.Menu
import android.view.MenuInflater
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountDashBoardBinding
import co.yap.modules.subaccounts.account.card.SubAccountCardFragment
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import javax.inject.Inject

class SubAccountDashBoardFragment :
    BaseNavViewModelFragment<FragmentSubAccountDashBoardBinding, ISubAccountDashBoard.State, SubAccountDashBoardVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    override fun getBindingVariable() = BR.subAccountDashBoardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_dash_board
    override var toolBarTitle: String? = "Household"

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.adapter.set(adapter)
    }


}