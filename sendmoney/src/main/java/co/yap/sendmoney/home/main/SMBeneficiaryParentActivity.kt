package co.yap.sendmoney.home.main

import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class SMBeneficiaryParentActivity : BaseBindingActivity<ISMBeneficiaryParent.ViewModel>(),
    INavigator,
    IFragmentHolder {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_sm_beneficiary_parent

    override val viewModel: ISMBeneficiaryParent.ViewModel
        get() = ViewModelProviders.of(this).get(SMBeneficiaryParentViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@SMBeneficiaryParentActivity,
            R.id.sm_beneficiary_parent_nav_host_fragment
        )

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> onBackPressed()
            R.id.ivRightIcon -> {
            }
        }
    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.sm_beneficiary_parent_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}
