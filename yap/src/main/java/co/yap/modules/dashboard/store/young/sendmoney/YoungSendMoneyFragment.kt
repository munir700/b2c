package co.yap.modules.dashboard.store.young.sendmoney

import co.yap.R
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.BR
import co.yap.databinding.FragmentYoungSendMoneyBinding

class YoungSendMoneyFragment : BaseNavViewModelFragment<FragmentYoungSendMoneyBinding,IYoungSendMoney.State,YoungSendMoneyVM>(){
    override fun getBindingVariable()= BR.viewModel
    override fun getLayoutId()= R.layout.fragment_young_send_money
    override fun onClick(id: Int) {
    }
}