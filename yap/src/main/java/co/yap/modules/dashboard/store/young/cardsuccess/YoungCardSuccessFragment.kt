package co.yap.modules.dashboard.store.young.cardsuccess


import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentCardSuccessBinding
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungCardSuccessFragment:
    BaseNavViewModelFragment<FragmentCardSuccessBinding, IYoungCardSuccess.State, YoungCardSuccessVM>() {
    override fun getBindingVariable()= BR.viewModel
    override fun getLayoutId()= R.layout.fragment_card_success
}