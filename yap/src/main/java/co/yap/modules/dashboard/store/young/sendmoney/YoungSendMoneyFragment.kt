package co.yap.modules.dashboard.store.young.sendmoney

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungSendMoneyBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.hideKeyboard

class YoungSendMoneyFragment :
    BaseNavViewModelFragment<FragmentYoungSendMoneyBinding, IYoungSendMoney.State, YoungSendMoneyVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_send_money
    override fun getToolBarTitle() = "Send money to Lina"
    override fun toolBarVisibility() = true
    override fun setDisplayHomeAsUpEnabled() = true
    override fun onClick(id: Int) {
        requireContext().hideKeyboard()
        navigate(YoungSendMoneyFragmentDirections.actionYoungSendMoneyFragmentToYoungSendMoneySuccessFragment())
    }
}