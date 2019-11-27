package co.yap.modules.yapit.sendmoney.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.yapit.sendmoney.interfaces.ISendMoney
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import kotlinx.android.synthetic.main.activity_send_money_home.*
import kotlinx.android.synthetic.main.layout_send_beneficiaries_toolbar.view.*

class SendMoneyHomeActivity : BaseBindingActivity<ISendMoney.ViewModel>(), INavigator,
    IFragmentHolder {

     private var addIcon: ImageView? = null

    public companion object {
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
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
        addIcon = toolbar.tbBtnAddBeneficiary

    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
        PersonalDetailsFragment.checkMore = false
        PersonalDetailsFragment.checkScanned = false
        DocumentsDashboardActivity.isFromMoreSection = false
        DocumentsDashboardActivity.hasStartedScanner = false
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.send_money_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }
    open fun getAddIcon():ImageView?{
        return addIcon
    }
}