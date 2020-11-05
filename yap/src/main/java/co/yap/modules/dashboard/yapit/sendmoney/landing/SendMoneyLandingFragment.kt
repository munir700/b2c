package co.yap.modules.dashboard.yapit.sendmoney.landing

import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.BR
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyLandingViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyBaseFragment


class SendMoneyLandingFragment : SendMoneyBaseFragment<ISendMoneyLanding.ViewModel>(),
    ISendMoneyLanding.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_send_money_landing

    override val viewModel: ISendMoneyLanding.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyLandingViewModel::class.java)
}