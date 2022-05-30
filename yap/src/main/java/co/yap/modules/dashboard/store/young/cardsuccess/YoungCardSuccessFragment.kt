package co.yap.modules.dashboard.store.young.cardsuccess

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardSuccessBinding

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoungCardSuccessFragment :
    BaseNavViewModelFragmentV2<FragmentCardSuccessBinding, IYoungCardSuccess.State, YoungCardSuccessVM>() {
    override fun getBindingVariable() = BR.viewModel
    override val viewModel: YoungCardSuccessVM by viewModels()

    override fun getLayoutId() = R.layout.fragment_card_success
    override fun toolBarVisibility() = true
    override fun getToolBarTitle()=getString(R.string.screen_email_verification_display_text_title)
    override fun setDisplayHomeAsUpEnabled() = false
    override fun onClick(id: Int) {
        navigate(YoungCardSuccessFragmentDirections.actionYoungCardSuccessFragment2ToYoungSendMoneyFragment())
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }
}