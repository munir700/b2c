package co.yap.modules.dashboard.store.household.success

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserSuccessBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHAddUserSuccessFragment :
    BaseNavViewModelFragment<FragmentHhAddUserSuccessBinding, IHHAddUserSuccess.State, HHAddUserSuccessVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_success
}
