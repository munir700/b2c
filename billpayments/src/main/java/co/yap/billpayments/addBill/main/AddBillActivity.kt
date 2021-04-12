package co.yap.billpayments.addBill.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class AddBillActivity : BaseBindingActivity<IAddBill.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_bill_payments_home

    override val viewModel: IAddBill.ViewModel
        get() = ViewModelProviders.of(this).get(AddBillViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@AddBillActivity,
            R.id.add_bill_nav_host_fragment
        )

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.bill_payments_main_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    val onToolBarClick = Observer<Int> {
        when (it) {
            R.id.ivLeftIcon -> {
                onBackPressed()
            }
        }
    }
}
