package co.yap.billpayments.payall.main

import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class PayAllMainActivity : BaseBindingActivity<IPayAllMain.ViewModel>(), IPayAllMain.View,
    IFragmentHolder, INavigator {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_pay_all_main
    override val viewModel: IPayAllMain.ViewModel
        get() = ViewModelProviders.of(this).get(PayAllMainViewModel::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@PayAllMainActivity,
            R.id.pay_all_nav_host_fragment
        )

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.pay_bill_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                onBackPressed()
            }
        }
    }
}
