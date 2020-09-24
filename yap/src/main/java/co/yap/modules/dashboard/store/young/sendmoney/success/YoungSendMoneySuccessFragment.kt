package co.yap.modules.dashboard.store.young.sendmoney.success

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungSendMoneySuccessBinding
import co.yap.translation.Strings.screen_domestic_funds_transfer_display_text_title
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungSendMoneySuccessFragment :
    BaseNavViewModelFragment<FragmentYoungSendMoneySuccessBinding, IYoungSendMoneySuccess.State, YoungSendMoneySuccessVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_send_money_success
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() = getString(screen_domestic_funds_transfer_display_text_title)
    override fun setDisplayHomeAsUpEnabled() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGoToDashboard -> navigate(YoungSendMoneySuccessFragmentDirections.actionYoungSendMoneySuccessFragmentToYoungSubAccountsFragment())
        }
    }
}