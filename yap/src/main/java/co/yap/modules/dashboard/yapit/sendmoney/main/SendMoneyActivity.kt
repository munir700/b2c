package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class SendMoneyActivity : BaseBindingActivity<ISendMoneyMain.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money

    override val viewModel: ISendMoneyMain.ViewModel = ViewModelProviders.of(this).get(
        SendMoneyMainViewModel::class.java
    )
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@SendMoneyActivity, R.id.send_money_nav_host_fragment_new)
}