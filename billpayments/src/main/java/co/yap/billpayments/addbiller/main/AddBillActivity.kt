package co.yap.billpayments.addbiller.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ActivityAddBillBinding
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class AddBillActivity : BaseBindingActivity<ActivityAddBillBinding ,IAddBill.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_add_bill

    override val viewModel: IAddBill.ViewModel
        get() = ViewModelProviders.of(this).get(AddBillViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@AddBillActivity,
            R.id.add_bill_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(ExtraKeys.BILL_PROVIDER.name)) {
            viewModel.selectedBillProvider = intent.getValue(
                ExtraKeys.BILL_PROVIDER.name,
                ExtraType.PARCEABLE.name
            ) as BillProviderModel?
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.add_bill_navigation)
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
