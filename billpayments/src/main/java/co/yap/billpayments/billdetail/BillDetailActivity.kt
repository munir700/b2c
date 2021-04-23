package co.yap.billpayments.billdetail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class BillDetailActivity : BaseBindingActivity<IBillDetail.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_bill_detail

    override val viewModel: IBillDetail.ViewModel
        get() = ViewModelProviders.of(this).get(BillDetailViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@BillDetailActivity,
            R.id.bill_detail_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(ExtraKeys.SELECTED_BILL.name)) {
            viewModel.selectedBill = intent.getValue(
                ExtraKeys.SELECTED_BILL.name,
                ExtraType.PARCEABLE.name
            ) as ViewBillModel?
        }
        if (intent.hasExtra(ExtraKeys.SELECTED_BILL.name)) {
            viewModel.selectedBillPosition = intent.getValue(
                ExtraKeys.SELECTED_BILL_POSITION.name,
                ExtraType.INT.name
            ) as Int?
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.bill_detail_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                onBackPressed()
            }
        }
    }
}
