package co.yap.modules.dashboard.store.household.contact

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserContactBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHAddUserContactFragment :
    BaseNavViewModelFragment<FragmentHhAddUserContactBinding, IHHAddUserContact.State, HHAddUserContactVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_contact
}
