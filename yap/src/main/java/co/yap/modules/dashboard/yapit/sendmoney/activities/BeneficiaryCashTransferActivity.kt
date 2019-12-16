package co.yap.modules.dashboard.yapit.sendmoney.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.BeneficiaryCashTransferViewModel
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class BeneficiaryCashTransferActivity : BaseBindingActivity<IBeneficiaryCashTransfer.ViewModel>(),
    IFragmentHolder, INavigator {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_beneficiary_cash_transfer

    override val viewModel: IBeneficiaryCashTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryCashTransferViewModel::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@BeneficiaryCashTransferActivity,
            R.id.beneficiary_cash_transfer_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent != null) {
            viewModel.state.beneficiary =
                intent.getParcelableExtra(Constants.BENEFICIARY) as? Beneficiary?
        }
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.tbIvClose -> onBackPressed()
            R.id.tvRightToolbar -> this.finish()
        }
    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.beneficiary_cash_transfer_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}