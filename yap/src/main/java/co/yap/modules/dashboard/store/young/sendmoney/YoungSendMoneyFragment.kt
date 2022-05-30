package co.yap.modules.dashboard.store.young.sendmoney

import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungSendMoneyBinding

import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoungSendMoneyFragment :
    BaseNavViewModelFragmentV2<FragmentYoungSendMoneyBinding, IYoungSendMoney.State, YoungSendMoneyVM>() {
    override fun getBindingVariable() = BR.viewModel
    override val viewModel: YoungSendMoneyVM by viewModels()

    override fun getLayoutId() = R.layout.fragment_young_send_money
    override fun getToolBarTitle() = "Send money to Lina"
    override fun toolBarVisibility() = true
    override fun setDisplayHomeAsUpEnabled() = true
    override fun onClick(id: Int) {
        requireContext().hideKeyboard()
        navigate(YoungSendMoneyFragmentDirections.actionYoungSendMoneyFragmentToYoungSendMoneySuccessFragment())
    }
}