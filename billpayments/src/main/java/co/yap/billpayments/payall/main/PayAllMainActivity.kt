package co.yap.billpayments.payall.main

import android.os.Bundle
import androidx.activity.viewModels
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import com.google.gson.Gson

class PayAllMainActivity : BaseBindingActivity<IPayAllMain.ViewModel>(), IPayAllMain.View,
    IFragmentHolder, INavigator {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_pay_all_main
    private val payAllMainViewModel: PayAllMainViewModel by viewModels()
    override val viewModel: IPayAllMain.ViewModel
        get() = payAllMainViewModel
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@PayAllMainActivity,
            R.id.pay_all_nav_host_fragment
        )

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.pay_all_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.allBills.value =
            Gson().fromJson(
                intent.getStringExtra(ExtraKeys.ALL_BILLS.name),
                Array<ViewBillModel>::class.java
            ).toMutableList()
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivRightIcon -> {
                finish()
            }
        }
    }
}
