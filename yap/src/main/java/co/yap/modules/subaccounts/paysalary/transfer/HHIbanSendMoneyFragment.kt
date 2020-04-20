package co.yap.modules.subaccounts.paysalary.transfer

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhibanSendMoneyBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHIbanSendMoneyFragment :
    BaseNavViewModelFragment<FragmentHhibanSendMoneyBinding, IHHIbanSendMoney.State, HHIbanSendMoneyVM>() {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_hhiban_send_money
    override fun getToolBarTitle() = state.subAccount.value?.getFullName()
}