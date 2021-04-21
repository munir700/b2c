package co.yap.billpayments.paybill.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ActivityPayBillMainBinding
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class PayBillMainActivity : BaseBindingActivity<IPayBillMain.ViewModel>(), IPayBillMain.View,
    IFragmentHolder, INavigator {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_pay_bill_main
    override val viewModel: IPayBillMain.ViewModel
        get() = ViewModelProviders.of(this).get(PayBillMainViewModel::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@PayBillMainActivity,
            R.id.pay_bill_nav_host_fragment
        )

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.pay_bill_payments_main_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    val onToolBarClick = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> {
                finish()
            }
        }
    }

    override fun setObservers() {
        viewModel.onToolbarClickEvent.observe(this, onToolBarClick)
    }

    override fun removeObservers() {
        viewModel.onToolbarClickEvent.removeObserver(onToolBarClick)
    }

    override fun getViewBinding(): ActivityPayBillMainBinding =
        viewDataBinding as ActivityPayBillMainBinding

}