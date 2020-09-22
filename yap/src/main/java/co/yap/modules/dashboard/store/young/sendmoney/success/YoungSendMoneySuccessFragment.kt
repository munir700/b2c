package co.yap.modules.dashboard.store.young.sendmoney.success

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungSendMoneySuccessBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
class YoungSendMoneySuccessFragment : BaseNavViewModelFragment<FragmentYoungSendMoneySuccessBinding, IYoungSendMoneySuccess.State,YoungSendMoneySuccessVM>() {

    override fun getBindingVariable()= BR.viewModel

    override fun getLayoutId()= R.layout.fragment_young_send_money_success

    override fun onClick(id: Int) {
    }
}