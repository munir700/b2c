package co.yap.modules.dashboard.yapit.sendmoney.activities

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.BeneficiaryCashTransferViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
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
}