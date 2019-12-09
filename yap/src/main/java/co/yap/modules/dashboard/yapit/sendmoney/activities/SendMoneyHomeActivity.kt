package co.yap.modules.dashboard.yapit.sendmoney.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.ISendMoney
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyViewModel
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class SendMoneyHomeActivity : BaseBindingActivity<ISendMoney.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SendMoneyHomeActivity::class.java)
            return intent
        }

    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_send_money_home

    override val viewModel: ISendMoney.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@SendMoneyHomeActivity, R.id.send_money_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, backButtonObserver)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
        PersonalDetailsFragment.checkMore = false
        PersonalDetailsFragment.checkScanned = false
        DocumentsDashboardActivity.isFromMoreSection = false
        DocumentsDashboardActivity.hasStartedScanner = false
    }

    private val backButtonObserver = Observer<Int> { onBackPressed() }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.send_money_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }
}