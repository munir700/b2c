package co.yap.modules.dashboard.store.young.cardsuccess

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentCardSuccessBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungCardSuccessFragment:
    BaseNavViewModelFragment<FragmentCardSuccessBinding, IYoungCardSuccess.State, YoungCardSuccessVM>() {
    override fun getBindingVariable()= BR.viewModel
    override fun getLayoutId()= R.layout.fragment_card_success
    override fun toolBarVisibility() = true
    override fun getToolBarTitle(): String? {
        return "Great !!"
    }
}