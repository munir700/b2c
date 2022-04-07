package co.yap.billpayments.paybill.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ActivityPayBillMainBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import com.google.android.material.snackbar.Snackbar

class PayBillMainActivity : BaseBindingActivity<ActivityPayBillMainBinding,IPayBillMain.ViewModel>(), IPayBillMain.View,
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
            supportFragmentManager.findFragmentById(R.id.pay_bill_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.billModel.value =
            intent.getValue(ExtraKeys.SELECTED_BILL.name, ExtraType.PARCEABLE.name) as ViewBillModel
    }

    override fun setObservers() {
        viewModel.toolBarClickEvent.observe(this, onToolBarClick)
        viewModel.errorEvent.observe(this, errorEvent)
    }

    val errorEvent = Observer<String> {
        if (!it.isNullOrEmpty())
            showErrorSnackBar(it)
        else
            hideErrorSnackBar()
    }


    private fun showErrorSnackBar(errorMessage: String) {
        showTextUpdatedAbleSnackBar(
            errorMessage,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    private fun hideErrorSnackBar() {
        cancelAllSnackBar()
    }

    val onToolBarClick = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> {
                finish()
            }
        }
    }

    override fun removeObservers() {
        viewModel.toolBarClickEvent.removeObserver(onToolBarClick)
    }

    override fun onDestroy() {
        removeObservers()
        super.onDestroy()
    }

    override fun getViewBinding(): ActivityPayBillMainBinding =
        viewDataBinding as ActivityPayBillMainBinding

}