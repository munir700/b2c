package co.yap.modules.dashboard.store.young.confirmrelationship

import co.yap.databinding.FragmentYoungConfirmRelationshipBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.BR
import co.yap.R

class YoungConfirmRelationshipFragment :
    BaseNavViewModelFragment<FragmentYoungConfirmRelationshipBinding, IYoungConfirmRelationship.State, YoungConfirmRelationshipVM>() {
    override fun getBindingVariable() = BR.yConfirmRelationshipViewModel
    override fun getLayoutId() = R.layout.fragment_young_confirm_relationship

}