package co.yap.modules.subaccounts.account.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountDashBoardBinding
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubAccountDashBoardFragment :
    BaseNavViewModelFragmentV2<FragmentSubAccountDashBoardBinding, ISubAccountDashBoard.State, SubAccountDashBoardVM>() {

    override val viewModel: SubAccountDashBoardVM by viewModels()
    lateinit var adapter: SectionsPagerAdapter

    override fun getBindingVariable() = BR.subAccountDashBoardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_dash_board
    override fun getToolBarTitle(): String? {
        return "Household"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO MAKE injectable
        adapter = SectionsPagerAdapter(requireActivity(), childFragmentManager)
        super.onViewCreated(view, savedInstanceState)

    }
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.adapter.set(adapter)
    }

    override fun onClick(id: Int) {
    }
}
