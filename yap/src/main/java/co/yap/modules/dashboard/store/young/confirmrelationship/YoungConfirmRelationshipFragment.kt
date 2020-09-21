package co.yap.modules.dashboard.store.young.confirmrelationship

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungConfirmRelationshipBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungConfirmRelationshipFragment :
    BaseNavViewModelFragment<FragmentYoungConfirmRelationshipBinding, IYoungConfirmRelationship.State, YoungConfirmRelationshipVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_confirm_relationship
    override fun onClick(id: Int) {
    }
}
