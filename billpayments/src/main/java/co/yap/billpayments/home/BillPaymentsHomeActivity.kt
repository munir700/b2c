package co.yap.billpayments.home

import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class BillPaymentsHomeActivity : BaseBindingActivity<IBillPayments.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_bill_payments_home

    override val viewModel: IBillPayments.ViewModel
        get() = ViewModelProviders.of(this).get(BillPaymentsViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@BillPaymentsHomeActivity, R.id.bill_payments_nav_host_fragment)

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.bill_payments_main_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> onBackPressed()
        }
    }
}